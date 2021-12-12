package br.com.parcelaae.app.domain.scheduling.model;

import br.com.parcelaae.app.domain.clinic.model.ClinicApiResponse;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class SchedulingApiResponse {
    private Long schedulingId;
    private ClinicApiResponse clinic;
    private SpecialtyApiModel specialty;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDateTime scheduledTo;
    private BigDecimal appointmentValue;
    private String appointmentTime;
}
