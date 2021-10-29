package br.com.parcelaae.app.services;

import br.com.parcelaae.app.security.UserSS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetUserProfile() {
        var userSS = UserSS.builder()
                .name("John Wick")
                .email("john@wick.com")
                .build();

        var userProfileDTO = userService.getUserProfile(userSS);

        assertThat(userProfileDTO.getName()).isEqualTo(userSS.getName());
        assertThat(userProfileDTO.getEmail()).isEqualTo(userSS.getEmail());
    }
}
