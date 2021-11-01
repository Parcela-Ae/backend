package br.com.parcelaae.app.domain.feedback.model;

import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.customer.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private Float rating;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public String getClinicName() {
        return clinic.getName();
    }
}
