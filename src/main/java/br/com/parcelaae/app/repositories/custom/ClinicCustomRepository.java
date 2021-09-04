package br.com.parcelaae.app.repositories.custom;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ClinicCustomRepository {

    private final EntityManager em;

    public List<Clinic> find(@NotNull ClinicFilter filter) {
        String sql = "";

        sql += " SELECT clinic ";
        sql += " FROM Clinic clinic ";

        if (filter.getName() != null) {
            sql += " WHERE clinic.name LIKE :name";
        }

        var query = em.createQuery(sql, Clinic.class);

        if (filter.getName() != null) {
            query.setParameter("name", "%" + filter.getName() + "%");
        }
        return query.getResultList();
    }
}
