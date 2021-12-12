package br.com.parcelaae.app.domain.specialty.model;

import br.com.parcelaae.app.domain.clinic.model.ClinicSpecialty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyApiModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Double appointmentValue;

    public SpecialtyApiModel(Specialty entity) {
        setId(entity.getId());
        setName(entity.getName());
    }

    public SpecialtyApiModel(ClinicSpecialty clinicSpecialty) {
        setId(clinicSpecialty.getSpecialty().getId());
        setName(clinicSpecialty.getSpecialty().getName());
        setAppointmentValue(clinicSpecialty.getAppointmentValue());
    }
}
