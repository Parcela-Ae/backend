package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.enums.TransactionType;
import br.com.parcelaae.app.services.validation.EnumNamePattern;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewTransactionDTO {
    private Long accountNumberOrigin;
    private Long accountNumberDestination;
    private String cpfCnpj;
    @DecimalMin("0.01")
    private Double value;
    @NotNull
    @EnumNamePattern(regexp = "RECHARGE|TRANSFER|PAYMENT")
    private TransactionType type;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private Integer installments;
}
