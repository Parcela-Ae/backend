package br.com.parcelaae.app.domain.scheduling.model;

import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.customer.model.Customer;
import br.com.parcelaae.app.domain.specialty.model.Specialty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Scheduling implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    private LocalDateTime scheduledTo;
    private BigDecimal appointmentValue;
    private String appointmentTime;
}
