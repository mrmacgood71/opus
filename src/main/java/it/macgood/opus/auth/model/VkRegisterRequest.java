package it.macgood.opus.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VkRegisterRequest {

    private String firstname;
    private String lastname;
    private String nickname;
    private String photo;
    private String email;
    private String password;
    private String dateOfBirth;
}
