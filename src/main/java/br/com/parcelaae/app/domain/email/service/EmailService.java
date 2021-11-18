package br.com.parcelaae.app.domain.email.service;

import br.com.parcelaae.app.domain.email.model.Email;
import br.com.parcelaae.app.domain.email.model.StatusEmail;
import br.com.parcelaae.app.domain.email.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class EmailService {

    private static final String SUBJECT = "Parcela Aê - Confirmação de email";
    private static final List<String> WHITE_LIST = Collections.singletonList("joao.lucas.sec@gmail.com");

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailOrigin;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    public Email sendEmail(Email emailModel) {
        var email = new Email();
        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException mailException) {
            log.error("failed to send email", mailException);
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            email = emailRepository.save(emailModel);
        }
        return email;
    }

    @Async
    public void send(String to, String email) {
        if (WHITE_LIST.contains(email)) {
            var emailModel = new Email();
            emailModel.setSendDateEmail(LocalDateTime.now());
            try {
                var mimeMessage = emailSender.createMimeMessage();
                var helper = new MimeMessageHelper(mimeMessage, "utf-8");

                helper.setText(email, true);
                helper.setTo(to);
                helper.setSubject(SUBJECT);
                helper.setFrom(emailOrigin);

                buildEmailModel(to, email, emailModel);

                emailSender.send(mimeMessage);

                emailModel.setStatusEmail(StatusEmail.SENT);
            } catch (MessagingException messagingException) {
                log.error("failed to send email", messagingException);
                emailModel.setStatusEmail(StatusEmail.ERROR);
            } finally {
                emailRepository.save(emailModel);
            }
        }
    }

    private void buildEmailModel(String to, String email, Email emailModel) {
        emailModel.setEmailTo(to);
        emailModel.setSubject(SUBJECT);
        emailModel.setEmailFrom(emailOrigin);
        emailModel.setText(email);
    }
}
