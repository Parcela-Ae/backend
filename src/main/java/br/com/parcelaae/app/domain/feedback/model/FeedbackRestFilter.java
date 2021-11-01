package br.com.parcelaae.app.domain.feedback.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FeedbackRestFilter {

    private Integer customerId;
    private Integer clinicId;
}
