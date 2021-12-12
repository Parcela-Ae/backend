package br.com.parcelaae.app.domain.security;

import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.domain.security.model.UserSS;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static br.com.parcelaae.app.core.constants.AppConstants.CLINIC;
import static br.com.parcelaae.app.core.constants.AppConstants.CUSTOMER;

@UtilityClass
public class SecurityUtil {

    public static UserSS getUserSS() {
        return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getTypeUser(UserSS userSS) {
        String typeUser = CUSTOMER;
        if (userSS.getAuthorities().contains(new SimpleGrantedAuthority(Profile.CLINIC.getDescription())))
            typeUser = CLINIC;

        return typeUser;
    }
}
