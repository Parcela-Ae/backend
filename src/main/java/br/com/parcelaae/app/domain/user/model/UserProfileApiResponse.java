package br.com.parcelaae.app.domain.user.model;

import br.com.parcelaae.app.domain.security.model.UserSS;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileApiResponse {

    private String accessToken;
    private Integer id;
    private String email;
    private String name;
    private String typeUser;
    private Long accountNumber;

    public UserProfileApiResponse(UserSS userSS) {
        BeanUtils.copyProperties(userSS, this);
    }
}
