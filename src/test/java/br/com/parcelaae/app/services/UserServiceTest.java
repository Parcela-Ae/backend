package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.security.UserSS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private CreditService creditService;

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
