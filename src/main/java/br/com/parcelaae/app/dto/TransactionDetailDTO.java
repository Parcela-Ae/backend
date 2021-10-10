package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.enums.TransactionStatus;
import br.com.parcelaae.app.domain.enums.TransactionType;
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
public class TransactionDetailDTO {

    private Long id;
    private String origin;
    private String destination;
    private TransactionType type;
    private TransactionStatus status;
    private Double value;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private LocalDateTime operationDate;
}
