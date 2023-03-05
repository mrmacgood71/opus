package it.macgood.opus.works;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestWork {
    private String name;
    private String description;
    private MultipartFile file;
    private MultipartFile previewPhoto;
    private String userEmail;
}
