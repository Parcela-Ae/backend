package br.com.parcelaae.app.domain.city.repository.impl;

import br.com.parcelaae.app.domain.city.repository.CityRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
@Repository
public class CityRepositoryCustomImpl implements CityRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<String> listAll() {

        var sql = "SELECT DISTINCT address.city " +
                "FROM Address address " +
                "INNER JOIN Clinic clinic ON address.user.id = clinic.id " +
                "ORDER BY address.city ASC ";

        var typedQuery = em.createQuery(sql, String.class);

        return typedQuery.getResultList();
    }
}
