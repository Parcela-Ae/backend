package br.com.parcelaae.app.repositories.custom;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ClinicCustomRepository {

    private final EntityManager em;

    public ClinicCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<Clinic> find(ClinicFilter filter) {
        String sql = "";

        sql += " SELECT clinic ";
        sql += " FROM Clinic clinic ";

        if (filter != null && filter.getName() != null) {
            sql += " WHERE clinic.name LIKE :name";
        }

        var query = em.createQuery(sql, Clinic.class);

        if (filter != null && filter.getName() != null) {
            query.setParameter("name", "%" + filter.getName() + "%");
        }
        return query.getResultList();
    }
}
