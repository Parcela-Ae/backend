package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.dto.UserProfileDTO;
import br.com.parcelaae.app.security.SecurityUtil;
import br.com.parcelaae.app.security.UserSS;
import br.com.parcelaae.app.services.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void shouldGetUserProfile() {
        var userProfileDTOExpected = UserProfileDTO.builder()
                .id(1)
                .name("John Wick")
                .email("john@wick.com")
                .build();

        var userSS = UserSS.builder().build();

        try(MockedStatic<SecurityUtil> securityUtilMockedStatic = Mockito.mockStatic(SecurityUtil.class)) {
            securityUtilMockedStatic.when(SecurityUtil::getUserSS).thenReturn(userSS);

            when(userService.getUserProfile(any())).thenReturn(userProfileDTOExpected);

            var response = userController.getUserProfile();
            var userProfileDTOActual = response.getBody();

            assertThat(response).isNotNull();
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(userProfileDTOActual).usingRecursiveComparison().isEqualTo(userProfileDTOExpected);
        }
    }
}
