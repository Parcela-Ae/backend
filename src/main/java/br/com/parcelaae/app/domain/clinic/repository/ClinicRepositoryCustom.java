package br.com.parcelaae.app.domain.clinic.repository;

import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface ClinicRepositoryCustom {

    List<Clinic> find(@NotNull ClinicRestFilter filter);

    void addAppointmentValue(Integer clinicId, Integer specialtyId, BigDecimal appointmentValue);

    BigDecimal getAppointmentValue(Integer clinicId, Integer specialtyId);
}
