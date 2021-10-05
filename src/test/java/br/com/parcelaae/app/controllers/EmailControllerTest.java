package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Email;
import br.com.parcelaae.app.dto.EmailDto;
import br.com.parcelaae.app.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private EmailService emailService;

    @Test
    void shouldSendAnEmail() {
        var emailDto = EmailDto.builder()
                .ownerRef("Logan")
                .emailFrom("logan@wolverine.com")
                .emailTo("john@wick.com")
                .subject("Invitation to the new team")
                .text("Hey Mr. Wick! You were invited to join a new team, Do you have interested?")
                .build();
        var emailExpected = new Email();
        BeanUtils.copyProperties(emailDto, emailExpected);

        var response = emailController.sendEmail(emailDto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(emailExpected);
        verify(emailService, times(1)).sendEmail(any());
    }
}
