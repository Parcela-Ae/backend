package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.dto.UserProfileDTO;
import br.com.parcelaae.app.security.UserSS;
import br.com.parcelaae.app.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CreditService creditService;

    public UserProfileDTO getUserProfile(UserSS userSS) {
        var userProfileDTO = new UserProfileDTO(userSS);
        var credit = creditService.findByUserId(userSS.getId());
        userProfileDTO.setAccountNumber(credit.getId());
        return userProfileDTO;
    }

    public static UserSS getAuthenticatedUser() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void validateIfUserHasAuthoritation(Integer userId) {
        var user = getAuthenticatedUser();
        if (isAnUserValid(userId, user))
            throw new AuthorizationException("Acesso negado");
    }

    private static boolean isAnUserValid(Integer userId, UserSS user) {
        return user ==null || !user.hasRole(Profile.ADMIN) && !userId.equals(user.getId());
    }
}
