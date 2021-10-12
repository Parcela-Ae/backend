package br.com.parcelaae.app.services.exceptions;

public class BalanceInsufficientException extends RuntimeException {

    public BalanceInsufficientException(String message) {
        super(message);
    }
}
