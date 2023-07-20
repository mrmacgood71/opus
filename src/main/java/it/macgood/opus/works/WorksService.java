package it.macgood.opus.works;

import it.macgood.opus.user.model.User;
import it.macgood.opus.user.repository.UserRepository;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorksService {

    private final UserRepository userRepository;
    private final WorksRepository worksRepository;

    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    Optional<List<Work>> findWorksByUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<List<Work>> worksByUser = worksRepository.findWorksByUserId(user.orElseThrow().getId());
        return worksByUser;
    }

    Optional<List<Work>> findWorksByUser(Integer id) {
        Optional<List<Work>> worksByUser = worksRepository.findWorksByUserId(id);
        return worksByUser;
    }

    String findPathByOriginalFilename(String filename) {
        return worksRepository.findPathByOriginalFileName(filename);
    }

    Optional<Work> findWorkById(Integer id) {

        return worksRepository.findWorkById(id);
    }

    void delete(Integer userId, Integer id) {
        List<Work> works = worksRepository.findWorksByUserId(userId).orElseThrow();

        works.forEach(work -> {
            if (Objects.equals(work.getId(), id)) {
                Work workById = worksRepository.findWorkById(id).orElseThrow(RuntimeException::new);
                worksRepository.delete(workById);

                deleteFile(workById.getPath());
                deleteFile(workById.getPreviewPhoto());
            } else {
                throw new RuntimeException("File not found");
            }
        });
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if (!file.delete()) {
            Path resolvedPath = Paths.get(file.getAbsolutePath(), "");
            file = resolvedPath.toFile();
            file.delete();
        }
    }

    boolean save(RequestWork work) {

        Path directory = null;
        String userDirectory = UPLOAD_DIRECTORY + work.getUserEmail() + "/works";

        try {
            directory =
                    Files.createDirectories(Paths.get(userDirectory, ""));
        } catch (IOException ignored) {
            directory = Paths.get(userDirectory, "");
        }

        MultipartFile file = work.getFile();
        MultipartFile previewPhoto = work.getPreviewPhoto();
        MultipartFile coverPhoto = work.getCoverPhoto();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String photoFileName = StringUtils.cleanPath(previewPhoto.getOriginalFilename());
        String coverFileName = StringUtils.cleanPath(coverPhoto.getOriginalFilename());

        try {
            Path path = Paths.get(directory.toString(), filename);
            Path photoPath = Paths.get(directory.toString(), photoFileName);
            Path coverPhotoPath = Paths.get(directory.toString(), coverFileName);
            if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(previewPhoto.getInputStream(), photoPath, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(coverPhoto.getInputStream(), coverPhotoPath, StandardCopyOption.REPLACE_EXISTING);
                Optional<User> user = userRepository.findByEmail(work.getUserEmail());
                try {
                    Work save = worksRepository.save(
                            Work.builder()
                                    .description(work.getDescription())
                                    .name(work.getName())
                                    .path(path.toString())
                                    .coverPhotoName(coverPhotoPath.toString())
                                    .links(work.getLinks())
                                    .tags(work.getTags())
                                    .originalFileName(filename)
                                    .previewPhoto(photoPath.toString())
                                    .build()
                    );

                    worksRepository.saveSequence(
                            user.orElseThrow().getId(),
                            worksRepository.findWorkByPath(path.toString())
                                    .orElseThrow(NonUniqueResultException::new)
                                    .getId()
                    );

                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } catch (NullPointerException | IOException e) {
            return false;
        }
    }
}
