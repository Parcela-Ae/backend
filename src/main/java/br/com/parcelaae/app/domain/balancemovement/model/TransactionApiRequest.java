package br.com.parcelaae.app.domain.balancemovement.model;

import br.com.parcelaae.app.core.validation.EnumNamePattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class TransactionApiRequest {
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
