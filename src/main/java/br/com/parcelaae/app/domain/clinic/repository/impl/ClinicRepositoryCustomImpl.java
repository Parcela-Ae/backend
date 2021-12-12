package br.com.parcelaae.app.domain.clinic.repository.impl;

import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.clinic.repository.ClinicRepositoryCustom;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.isNull;

@AllArgsConstructor
public class ClinicRepositoryCustomImpl implements ClinicRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Clinic> find(@NotNull ClinicRestFilter filter) {
        var sql = new StringBuilder();
        var isFirst = true;

        sql.append(" SELECT clinic ");
        sql.append(" FROM Clinic clinic ");

        if (!isNullOrEmpty(filter.getCity()))
            sql.append(" INNER JOIN Address address ON clinic.id = address.user.id ");

        if (!isNullOrEmpty(filter.getSpecialty()))
            sql.append(" INNER JOIN clinic.specialties specialty ");

        if (!isNullOrEmpty(filter.getName())) {
            sql.append(" WHERE clinic.name LIKE :name ");
            isFirst = false;
        }

        if (!isNullOrEmpty(filter.getCity()) && isFirst) {
            sql.append(" WHERE address.city LIKE :city ");
            isFirst = false;
        } else if (!isNullOrEmpty(filter.getCity())) {
            sql.append(" AND address.city LIKE :city ");
        }

        if (!isNullOrEmpty(filter.getSpecialty()) && isFirst) {
            sql.append(" WHERE specialty.specialty.name LIKE :specialty ");
        } else if (!isNullOrEmpty(filter.getSpecialty())) {
            sql.append(" AND specialty.specialty.name LIKE :specialty ");
        }

        var query = em.createQuery(sql.toString(), Clinic.class);

        if (!isNullOrEmpty(filter.getName())) {
            query.setParameter("name", "%" + filter.getName() + "%");
        }

        if (!isNullOrEmpty(filter.getCity())) {
            query.setParameter("city", "%" + filter.getCity() + "%");
        }

        if (!isNullOrEmpty(filter.getSpecialty()))
            query.setParameter("specialty", "%" + filter.getSpecialty() + "%");

        return query.getResultList();
    }

    @Override
    public void addAppointmentValue(Integer clinicId, Integer specialtyId, BigDecimal appointmentValue) {
        em.joinTransaction();
        if (isNull(clinicId) || isNull(specialtyId) || isNull(appointmentValue))
            throw new IllegalArgumentException("Argumentos insuficientes para a solicitação");

        var sql = "UPDATE clinic_specialties SET appointment_value = :appointmentValue "
                + "WHERE clinic_id = :clinicId AND specialties_id = :specialtyId";

        var query = em.createNativeQuery(sql);

        query.setParameter("appointmentValue", appointmentValue);
        query.setParameter("clinicId", clinicId);
        query.setParameter("specialtyId", specialtyId);

        query.executeUpdate();
    }

    @Override
    public BigDecimal getAppointmentValue(Integer clinicId, Integer specialtyId) {

        if (isNull(clinicId) || isNull(specialtyId))
            throw new IllegalArgumentException("Argumentos insuficientes para a solicitação");

        var sql = "SELECT CS.appointment_value FROM clinic_specialties as CS "
                + "WHERE clinic_id = :clinicId AND specialties_id = :specialtyId";

        var query = em.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("specialtyId", specialtyId);

        var appointmentValue = (Double) query.getSingleResult();
        return BigDecimal.valueOf(appointmentValue);
    }
}
