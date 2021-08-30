package br.com.parcelaae.app.security;

import br.com.parcelaae.app.domain.enums.Profile;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {

    public static UserSS getUserSS() {
        return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getTypeUser(UserSS userSS) {
        String typeUser = "CLIENTE";
        if (userSS.getAuthorities().contains(new SimpleGrantedAuthority(Profile.CLINIC.getDescription())))
            typeUser = "CLINICA";

        return typeUser;
    }
}
