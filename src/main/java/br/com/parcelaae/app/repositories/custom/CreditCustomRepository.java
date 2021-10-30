package br.com.parcelaae.app.repositories.custom;

import br.com.parcelaae.app.domain.Credit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Repository
public class CreditCustomRepository {

    private final EntityManager em;

    private static final String QUERY_BASE = " SELECT credit FROM Credit credit ";

    public Credit findByUserId(Integer userId) {

        String sql = QUERY_BASE +
                " WHERE credit.user.id = :userId ";

        var query = em.createQuery(sql, Credit.class);

        query.setParameter("userId", userId);

        return query.getSingleResult();
    }

    public Credit findByCpf(@NotNull String cpf) {

        String sql = QUERY_BASE +
                " INNER JOIN Customer customer ON credit.user.id = customer.id " +
                " WHERE customer.cpf = :cpf ";

        var query = em.createQuery(sql, Credit.class);

        query.setParameter("cpf", cpf);

        return query.getSingleResult();
    }

    public Credit findByCnpj(String cnpj) {

        String sql = QUERY_BASE +
                " INNER JOIN Clinic clinic ON credit.user.id = clinic.id " +
                " WHERE clinic.cnpj = :cnpj ";

        var query = em.createQuery(sql, Credit.class);

        query.setParameter("cnpj", cnpj);

        return query.getSingleResult();
    }
}
