package br.com.parcelaae.app.domain.feedback.model;

import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.customer.model.Customer;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackApiRequest {

    @NotNull(message = "Preenchimento obrigatório")
    private Integer customerId;

    @NotNull(message = "Preenchimento obrigatório")
    private Integer clinicId;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 250, message = "O tamanho deve ser entre 5 e 250 caracteres")
    private String message;

    @NotNull(message = "Preenchimento obrigatório")
    private Float rating;

    public static Feedback toEntity(FeedbackApiRequest feedbackApiRequest) {
        var customer = new Customer(feedbackApiRequest.getCustomerId());
        var clinic = new Clinic(feedbackApiRequest.getClinicId());
        return Feedback.builder()
                .customer(customer)
                .clinic(clinic)
                .message(feedbackApiRequest.getMessage())
                .rating(feedbackApiRequest.getRating())
                .build();

    }
}
