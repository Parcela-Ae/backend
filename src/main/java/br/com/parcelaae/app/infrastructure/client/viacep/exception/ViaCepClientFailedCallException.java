package br.com.parcelaae.app.infrastructure.client.viacep.exception;

public class ViaCepClientFailedCallException extends RuntimeException {

    public ViaCepClientFailedCallException(String message) {
        super(message);
    }
}
