package br.com.parcelaae.app.domain.user.service;

import br.com.parcelaae.app.core.exception.AuthorizationException;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.user.model.UserProfileApiResponse;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
import br.com.parcelaae.app.domain.security.model.UserSS;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static br.com.parcelaae.app.domain.security.SecurityUtil.getTypeUser;
import static java.util.Objects.isNull;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CreditService creditService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email);

        var userSS = new UserSS(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getProfiles());
        userSS.setTypeUser(getTypeUser(userSS));
        return userSS;
    }

    public UserProfileApiResponse getUserProfile(UserSS userSS) {
        var userProfileDTO = new UserProfileApiResponse(userSS);
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
        return isNull(user) || !user.hasRole(Profile.ADMIN) && !userId.equals(user.getId());
    }
}
