package br.com.parcelaae.app.domain.user;

import br.com.parcelaae.app.builders.UserAux;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import br.com.parcelaae.app.domain.user.model.Profile;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
import br.com.parcelaae.app.domain.security.model.UserSS;
import br.com.parcelaae.app.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final String USER_EMAIL = "user@email.com";
    public static final int ID = 1;
    public static final String PASSWORD = "123456";
    public static final String USER_NAME = "John Wick";
    public static final String TYPE_USER = "CLIENTE";

    @InjectMocks
    private UserService userService;

    @Mock
    private CreditService creditService;

    @Mock
    private UserRepository userRepository;

    private User userExpected;

    private UserSS userSSExpected;

    private final Set<Integer> profiles = Set.of(1, 2);

    private final Set<Profile> profilesByEnum = Set.of(Profile.ADMIN, Profile.CUSTOMER);

    @BeforeEach
    void setUp() {
        var authorities = profilesByEnum.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());

        userExpected = UserAux.builder()
                .id(ID)
                .email(USER_EMAIL)
                .password(PASSWORD)
                .name(USER_NAME)
                .profiles(profiles)
                .build();

        userSSExpected = UserSS.builder()
                .id(ID)
                .email(USER_EMAIL)
                .password(PASSWORD)
                .name(USER_NAME)
                .typeUser(TYPE_USER)
                .authorities(authorities)
                .build();
    }

    @Test
    void shouldLoadUserByUsername() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(userExpected));

        var userSSActual = userService.loadUserByUsername(USER_EMAIL);

        var authoritiesExpected = convertAuthority(userSSExpected.getAuthorities());
        var authoritiesActual = convertAuthority(userSSActual.getAuthorities());
        var typeUserExpected = userSSExpected.getTypeUser();
        var typeUserActual = ((UserSS) userSSActual).getTypeUser();

        assertThat(userSSActual.getUsername()).isEqualTo(userSSExpected.getUsername());
        assertThat(userSSActual.getPassword()).isEqualTo(userSSExpected.getPassword());
        assertThat(authoritiesActual).isEqualTo(authoritiesExpected);
        assertThat(typeUserActual).isEqualTo(typeUserExpected);
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenLoadUserByUsernameIsCalled() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        var usernameNotFoundException = catchThrowableOfType(
                () -> userService.loadUserByUsername(USER_EMAIL), UsernameNotFoundException.class);

        assertThat(usernameNotFoundException)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(USER_EMAIL);
    }

    private String convertAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(Object::toString).sorted().collect(Collectors.toList()).toString();
    }


    @Test
    void shouldGetUserProfile() {
        var userSS = UserSS.builder()
                .name("John Wick")
                .email("john@wick.com")
                .build();
        var credit = Credit.builder().id(12345L).build();

        when(creditService.findByUserId(userSS.getId())).thenReturn(credit);

        var userProfileDTO = userService.getUserProfile(userSS);

        assertThat(userProfileDTO.getName()).isEqualTo(userSS.getName());
        assertThat(userProfileDTO.getEmail()).isEqualTo(userSS.getEmail());
        assertThat(userProfileDTO.getAccountNumber()).isEqualTo(credit.getId());
    }
}
