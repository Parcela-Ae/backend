package br.com.parcelaae.app.domain.balancemovement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TransactionApiResponse {

    private Long id;
    private Double value;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime operationDate;
    private TransactionType type;
}
