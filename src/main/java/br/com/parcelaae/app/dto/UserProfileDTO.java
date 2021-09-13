package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.security.UserSS;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {

    private String accessToken;
    private Integer id;
    private String email;
    private String name;
    private String typeUser;

    public UserProfileDTO(UserSS userSS) {
        BeanUtils.copyProperties(userSS, this);
    }
}
