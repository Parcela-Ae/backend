package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.security.UserSS;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    private String accessToken;
    private Integer id;
    private String email;
    private String name;
    private String typeUser;
    private Long accountNumber;

    public UserProfileDTO(UserSS userSS) {
        BeanUtils.copyProperties(userSS, this);
    }
}
