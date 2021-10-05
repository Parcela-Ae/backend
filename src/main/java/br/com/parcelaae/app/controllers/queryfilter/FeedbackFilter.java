package br.com.parcelaae.app.controllers.queryfilter;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FeedbackFilter {

    private Integer customerId;
    private Integer clinicId;
}
