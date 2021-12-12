package br.com.parcelaae.app.domain.scheduling.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SchedulingApiRequest {

    //PAYMENT
    private Long accountNumberOrigin;
    private String cpfCnpj;
    @DecimalMin("0.01")
    private Double value;

    //SCHEDULING
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer clinicId;
    @NotNull
    private Integer specialtyId;
    @NotNull
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime scheduledTo;
}
