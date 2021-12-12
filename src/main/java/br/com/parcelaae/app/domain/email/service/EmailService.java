package br.com.parcelaae.app.domain.email.service;

import br.com.parcelaae.app.domain.email.model.Email;
import br.com.parcelaae.app.domain.enums.StatusEmail;
import br.com.parcelaae.app.domain.email.repository.EmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@AllArgsConstructor
@Service
public class EmailService {

    private final EmailRepository emailRepository;

    private final JavaMailSender emailSender;

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
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            email = emailRepository.save(emailModel);
        }
        return email;
    }
}
