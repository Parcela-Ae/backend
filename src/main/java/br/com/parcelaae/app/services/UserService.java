package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.dto.UserProfileDTO;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.security.UserSS;
import br.com.parcelaae.app.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User insert(User user) {
        return userRepository.save(user);
    }

    /**
     * // TODO: 06/06/2021 Criar método para atualizar somente campos específicos
     */
    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Integer usuarioId) {
        userRepository.deleteById(usuarioId);
    }

    public UserProfileDTO getUserProfile(UserSS userSS) {
        return new UserProfileDTO(userSS);
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
