package it.macgood.opus.works;

import it.macgood.opus.user.model.Career;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestWork {
    private String name;
    private String description;
    private MultipartFile file;
    private MultipartFile previewPhoto;
    private MultipartFile coverPhoto;
    private String userEmail;
    private String links;
    private Career tags;
}
