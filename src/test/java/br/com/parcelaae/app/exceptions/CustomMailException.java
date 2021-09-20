package br.com.parcelaae.app.exceptions;

import org.springframework.mail.MailException;

public class CustomMailException extends MailException {

    public CustomMailException(String msg) {
        super(msg);
    }
}
