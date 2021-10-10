package br.com.parcelaae.app.controllers.exceptions;

public class BalanceInsufficientException extends Exception {

    public BalanceInsufficientException(String message) {
        super(message);
    }
}
