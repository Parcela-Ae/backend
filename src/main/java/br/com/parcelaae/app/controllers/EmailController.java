package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.email.model.Email;
import br.com.parcelaae.app.domain.email.model.EmailApiRequest;
import br.com.parcelaae.app.domain.email.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Email> sendEmail(@RequestBody @Valid EmailApiRequest emailApiRequest) {
        Email emailModel = new Email();
        BeanUtils.copyProperties(emailApiRequest, emailModel);
        emailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
}
