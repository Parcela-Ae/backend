package br.com.parcelaae.app.repositories.custom;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Repository
public class ClinicCustomRepository {

    private final EntityManager em;

    public List<Clinic> find(@NotNull ClinicFilter filter) {
        var sql = new StringBuilder();
        var isFirst = true;

        sql.append(" SELECT clinic ");
        sql.append(" FROM Clinic clinic ");

        if (nonNull(filter.getCity()))
            sql.append(" INNER JOIN Address address ON clinic.id = address.user.id ");

        if (nonNull(filter.getSpecialty()))
            sql.append(" INNER JOIN clinic.specialties specialty ");

        if (nonNull(filter.getName())) {
            sql.append(" WHERE clinic.name LIKE :name ");
            isFirst = false;
        }

        if (nonNull(filter.getCity()) && isFirst) {
            sql.append(" WHERE address.city LIKE :city ");
            isFirst = false;
        } else if (nonNull(filter.getCity())) {
            sql.append(" AND address.city LIKE :city ");
        }

        if (nonNull(filter.getSpecialty()) && isFirst) {
            sql.append(" WHERE specialty.name LIKE :specialty ");
        } else if (nonNull(filter.getSpecialty())) {
            sql.append(" AND specialty.name LIKE :specialty ");
        }

        var query = em.createQuery(sql.toString(), Clinic.class);

        if (nonNull(filter.getName())) {
            query.setParameter("name", "%" + filter.getName() + "%");
        }

        if (nonNull(filter.getCity())) {
            query.setParameter("city", "%" + filter.getCity() + "%");
        }

        if (nonNull(filter.getSpecialty()))
            query.setParameter("specialty", "%" + filter.getSpecialty() + "%");

        return query.getResultList();
    }
}
