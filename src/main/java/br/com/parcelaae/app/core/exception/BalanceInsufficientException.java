package br.com.parcelaae.app.core.exception;

public class BalanceInsufficientException extends RuntimeException {

    public BalanceInsufficientException(String message) {
        super(message);
    }
}
