package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.domain.Feedback;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewFeedbackDTO {

    @NotNull(message = "Preenchimento obrigatório")
    private Integer customerId;

    @NotNull(message = "Preenchimento obrigatório")
    private Integer clinicId;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 250, message = "O tamanho deve ser entre 5 e 250 caracteres")
    private String message;

    @NotNull(message = "Preenchimento obrigatório")
    private Float rating;

    public static Feedback toEntity(NewFeedbackDTO newFeedbackDTO) {
        var customer = new Customer(newFeedbackDTO.getCustomerId());
        var clinic = new Clinic(newFeedbackDTO.getClinicId());
        return Feedback.builder()
                .customer(customer)
                .clinic(clinic)
                .message(newFeedbackDTO.getMessage())
                .rating(newFeedbackDTO.getRating())
                .build();

    }
}
