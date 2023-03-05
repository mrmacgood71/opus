package it.macgood.opus.works;

import it.macgood.opus.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/works")
@RequiredArgsConstructor
public class WorksController {

    private final WorksService worksService;

    @GetMapping
    public List<Work> getWorks(
            Principal principal
    ) {
        return worksService.findWorksByUser(principal.getName()).orElseThrow();
    }

    @GetMapping("/byUser/{id}")
    public List<Work> getWorksByUserId(
            @PathVariable String id
    ) {
        return worksService.findWorksByUser(Integer.parseInt(id)).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<String> upload(
            Principal principal,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("photo") MultipartFile previewPhoto
    ) {

        boolean isSaved = worksService.save(
                RequestWork.builder()
                        .name(name)
                        .description(description)
                        .file(file)
                        .previewPhoto(previewPhoto)
                        .userEmail(principal.getName())
                        .build()
        );

        if (isSaved) {
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("File saved");
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("File not saved");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download    (
            @PathVariable Integer id,
            HttpServletRequest request
    ) {

        Resource resource = null;
        try {
            resource = new UrlResource("file", worksService.findWorkById(id).orElseThrow().getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException | NullPointerException ex) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).build();
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            Authentication auth,
            @PathVariable String id
    ) {
        User user = (User) auth.getPrincipal();

        worksService.delete(user.getId(), Integer.parseInt(id));

        return ResponseEntity.ok().build();
    }

}
