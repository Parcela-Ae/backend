package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.TransactionStatus;
import br.com.parcelaae.app.domain.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BalanceMovement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "origin_id")
    private Credit origin;

    @OneToOne
    @JoinColumn(name = "destination_id")
    private Credit destination;

    private TransactionType type;
    private Double value;
    private LocalDateTime operationDate;
    private TransactionStatus status;
}
