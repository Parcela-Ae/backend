package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.enums.TransactionType;
import br.com.parcelaae.app.services.validation.EnumNamePattern;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewTransactionDTO {

    @NotNull(message = "A conta de origem é obrigatória")
    private Long originCreditId;
    @NotNull(message = "A conta de destino é obrigatória")
    private Long destinationCreditId;
    @NotNull(message = "O valor da transação é obrigatório")
    private Double value;
    @EnumNamePattern(regexp = "RECHARGE|TRANSFER|PAYMENT")
    private TransactionType type;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String cardNumber;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String cardHolderName;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String expirationDate;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String cvv;
    @NotNull(message = "Preenchimento obrigatório")
    private Integer installments;
}
