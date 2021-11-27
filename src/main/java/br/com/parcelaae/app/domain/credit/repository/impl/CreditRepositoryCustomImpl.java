package br.com.parcelaae.app.domain.credit.repository.impl;

import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.repository.CreditRepositoryCustom;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class CreditRepositoryCustomImpl implements CreditRepositoryCustom {

    private final EntityManager em;

    private static final String QUERY_BASE = " SELECT credit FROM Credit credit ";

    @Override
    public Credit findByUserId(Integer userId) {

        String sql = QUERY_BASE +
                " WHERE credit.user.id = :userId ";

        var query = em.createQuery(sql, Credit.class);

        query.setParameter("userId", userId);

        return query.getSingleResult();
    }

    @Override
    public Credit findByCpf(@NotNull String cpf) {

        String sql = QUERY_BASE +
                " INNER JOIN Customer customer ON credit.user.id = customer.id " +
                " WHERE customer.cpf = :cpf ";

        var query = em.createQuery(sql, Credit.class);

        query.setParameter("cpf", cpf);

        return query.getSingleResult();
    }

    @Override
    public Credit findByCnpj(String cnpj) {

        String sql = QUERY_BASE +
                " INNER JOIN Clinic clinic ON credit.user.id = clinic.id " +
                " WHERE clinic.cnpj = :cnpj ";

        var query = em.createQuery(sql, Credit.class);

        query.setParameter("cnpj", cnpj);

        return query.getSingleResult();
    }
}
