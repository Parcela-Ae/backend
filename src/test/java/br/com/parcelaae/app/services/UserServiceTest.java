package br.com.parcelaae.app.services;

import br.com.parcelaae.app.builders.UserAux;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.security.UserSS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void shouldInsert() {
        var userToSave = UserAux.builder().build();
        var userExpected = UserAux.builder().id(1).build();

        when(userRepository.save(userToSave)).thenReturn(userExpected);

        var userActual = userService.insert(userToSave);

        assertThat(userActual).usingRecursiveComparison().isEqualTo(userExpected);
    }

    @Test
    void shouldUpdate() {
        var userToUpdate = UserAux.builder().build();
        var userExpected = UserAux.builder().id(1).build();

        when(userRepository.save(userToUpdate)).thenReturn(userExpected);

        var userActual = userService.update(userToUpdate);

        assertThat(userActual).usingRecursiveComparison().isEqualTo(userExpected);
    }

    @Test
    void shouldDelete() {
        var userId = 1;

        doNothing().when(userRepository).deleteById(1);

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

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
