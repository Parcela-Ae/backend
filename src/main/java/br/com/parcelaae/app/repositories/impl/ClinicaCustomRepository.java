package br.com.parcelaae.app.repositories.impl;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinica;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ClinicaCustomRepository {

    private final EntityManager em;

    public ClinicaCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<Clinica> find(ClinicFilter filter) {
        String sql = "";

        sql += " SELECT clinica ";
        sql += " FROM Clinica clinica ";

        if (filter != null && filter.getName() != null) {
            sql += " WHERE clinica.nome LIKE :name";
        }

        var query = em.createQuery(sql, Clinica.class);

        if (filter != null && filter.getName() != null) {
            query.setParameter("name", "%" + filter.getName() + "%");
        }
        return query.getResultList();
    }
}
