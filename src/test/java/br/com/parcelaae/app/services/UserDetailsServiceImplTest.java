package br.com.parcelaae.app.services;

import br.com.parcelaae.app.builders.UserAux;
import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.security.UserSS;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    public static final String USER_EMAIL = "user@email.com";
    public static final int ID = 1;
    public static final String PASSWORD = "123456";
    public static final String USER_NAME = "John Wick";
    public static final String TYPE_USER = "CLIENTE";

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

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
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(userExpected);

        var userSSActual = userDetailsService.loadUserByUsername(USER_EMAIL);

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
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(null);

        var usernameNotFoundException = catchThrowableOfType(
                () -> userDetailsService.loadUserByUsername(USER_EMAIL), UsernameNotFoundException.class);

        assertThat(usernameNotFoundException)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(USER_EMAIL);
    }

    private String convertAuthority(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(Object::toString).sorted().collect(Collectors.toList()).toString();
    }
}
