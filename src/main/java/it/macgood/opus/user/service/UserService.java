package it.macgood.opus.user.service;

import it.macgood.opus.user.model.User;
import it.macgood.opus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User findByEmail(String email) {return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));}

    public void save(User user) {
        userRepository.save(user);
    }


    public void updateUser(User user) {

        userRepository.updateUser(
                user.getId(),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getNickname()
        );

    }

    public void updatePhoto(
            Integer id,
            String email,
            MultipartFile photo
    ) throws IOException {

        Path directory = null;
        String userDirectory = UPLOAD_DIRECTORY + email + "/photo";

        try {
            directory =
                    Files.createDirectories(Paths.get(userDirectory, ""));
        } catch (IOException e) {
            directory = Paths.get(userDirectory, "");
        }

        String filename = StringUtils.cleanPath(photo.getOriginalFilename());
        Path path = Paths.get(directory.toString(), filename);
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            Files.copy(photo.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            try {
                userRepository.updatePhoto(id, path.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
