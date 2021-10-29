package br.com.parcelaae.app.controllers.exceptions;

import br.com.parcelaae.app.services.exceptions.AuthorizationException;
import br.com.parcelaae.app.services.exceptions.BalanceInsufficientException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        var err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", e.getMessage(), request.getRequestURI());
        e.getBindingResult().getFieldErrors()
                .forEach(x -> err.addError(x.getField(), x.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(BalanceInsufficientException.class)
    public ResponseEntity<StandardError> validation(BalanceInsufficientException balanceInsufficientException, HttpServletRequest request) {

        var err = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Saldo Insuficiente")
                .message(balanceInsufficientException.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException objectNotFoundException, HttpServletRequest request) {

        var err = StandardError.builder()
                .timestamp(System.currentTimeMillis())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Objeto não encontrado")
                .message(objectNotFoundException.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {

        var err = StandardError.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error("Erro de autorização")
                .message(e.getMessage())
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolation(ConstraintViolationException e, HttpServletRequest request) {
        var isCpfExisting = e.getConstraintName().contains("cpf");
        var isCnpjExisting = e.getConstraintName().contains("cnpj");
        var messageCpfOrCnpj = isCpfExisting ? "CPF já cadastrado" : "";

        if (messageCpfOrCnpj.isEmpty())
            messageCpfOrCnpj = isCnpjExisting ? "CNPJ já cadastrado" : "";

        var err = StandardError.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Violação de constraint")
                .message(isCpfExisting || isCnpjExisting ? messageCpfOrCnpj : e.getMessage())
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> illegalArguments(IllegalArgumentException e, HttpServletRequest request) {

        var err = StandardError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Erro argumentos inválidos")
                .message("Um ou mais argumentos estão inválidos")
                .timestamp(System.currentTimeMillis())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
