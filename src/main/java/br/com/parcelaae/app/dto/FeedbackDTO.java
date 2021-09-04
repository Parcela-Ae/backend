package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Feedback;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {

    private Integer customerId;

    private String customerName;

    private Integer clinicId;

    private String clinicName;

    private String message;

    private Float rating;

    public FeedbackDTO(Feedback entity) {
        this.customerId = entity.getCustomer().getId();
        this.customerName = entity.getCustomer().getName();
        this.clinicId = entity.getClinic().getId();
        this.clinicName = entity.getClinic().getName();
        this.message = entity.getMessage();
        this.rating = entity.getRating();
    }
}
