package br.com.parcelaae.app.domain.clinic.repository;

import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

@AllArgsConstructor
@Repository
public class ClinicCustomRepository {

    private final EntityManager em;

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
            sql.append(" WHERE specialty.name LIKE :specialty ");
        } else if (!isNullOrEmpty(filter.getSpecialty())) {
            sql.append(" AND specialty.name LIKE :specialty ");
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
}
