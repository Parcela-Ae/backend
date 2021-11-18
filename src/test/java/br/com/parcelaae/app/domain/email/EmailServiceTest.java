package br.com.parcelaae.app.domain.email;

import br.com.parcelaae.app.domain.email.model.Email;
import br.com.parcelaae.app.domain.email.model.StatusEmail;
import br.com.parcelaae.app.exceptions.CustomMailException;
import br.com.parcelaae.app.domain.email.repository.EmailRepository;
import br.com.parcelaae.app.domain.email.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    
    @InjectMocks
    private EmailService emailService;

    @Mock
    EmailRepository emailRepository;

    @Mock
    private JavaMailSender emailSender;

    private Email emailModel;

    private SimpleMailMessage message;

    private LocalDateTime sendDateEmailExpected;

    @BeforeEach
    void setUp() {
        emailModel = Email.builder()
                .emailFrom("from@from.com")
                .emailTo("emailto@emailto.com")
                .subject("Email Subject")
                .text("This is a sample of text")
                .build();

        message = new SimpleMailMessage();
        message.setFrom(emailModel.getEmailFrom());
        message.setTo(emailModel.getEmailTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getText());

        sendDateEmailExpected = LocalDateTime.of(2021, 9, 19, 21, 30);
    }

    @Test
    void shouldSendEmail() {
        var emailExpected = emailModel;

        try(MockedStatic<LocalDateTime> localDateTimeMock = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            localDateTimeMock.when(LocalDateTime::now).thenReturn(sendDateEmailExpected);

            when(emailRepository.save(emailModel)).thenReturn(emailExpected);

            var emailSent = emailService.sendEmail(emailModel);

            assertThat(emailSent.getStatusEmail()).isEqualTo(StatusEmail.SENT);
            assertThat(emailSent.getSendDateEmail()).isEqualTo(sendDateEmailExpected);
        }
    }

    @Test
    void shouldThrowMailExceptionWhenSendEmailMethodIsCalled() {
        var emailExpected = emailModel;

        doThrow(new CustomMailException("")).when(emailSender).send(message);
        when(emailRepository.save(emailModel)).thenReturn(emailExpected);

        var emailSent = emailService.sendEmail(emailModel);

        assertThat(emailSent.getStatusEmail()).isEqualTo(StatusEmail.ERROR);
    }
}
