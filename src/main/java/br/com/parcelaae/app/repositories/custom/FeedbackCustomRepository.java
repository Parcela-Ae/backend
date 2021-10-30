package br.com.parcelaae.app.repositories.custom;

import br.com.parcelaae.app.controllers.queryfilter.FeedbackFilter;
import br.com.parcelaae.app.domain.Feedback;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@AllArgsConstructor
@Repository
public class FeedbackCustomRepository {

    private final EntityManager em;

    public List<Feedback> find(@NotNull FeedbackFilter filter) {
        var sql = new StringBuilder();

        sql.append(" SELECT feedback ");
        sql.append(" FROM Feedback feedback ");

        if (nonNull(filter.getCustomerId()) && isNull(filter.getClinicId())) {
            sql.append(" WHERE feedback.customer.id = :customerId");
        }
        if (nonNull(filter.getClinicId()) && isNull(filter.getCustomerId())) {
            sql.append(" WHERE feedback.clinic.id = :clinicId");
        }

        if (nonNull(filter.getClinicId()) && isNull(filter.getCustomerId())) {
            sql.append(" WHERE feedback.clinic.id = :clinicId");
            sql.append(" AND feedback.customer.id = :customerId");
        }

        var query = em.createQuery(sql.toString(), Feedback.class);

        if (nonNull(filter.getCustomerId()) && isNull(filter.getClinicId())) {
            query.setParameter("customerId", filter.getCustomerId());
        }
        if (nonNull(filter.getClinicId()) && isNull(filter.getCustomerId())) {
            query.setParameter("clinicId", filter.getClinicId());
        }
        if (nonNull(filter.getClinicId()) && isNull(filter.getCustomerId())) {
            query.setParameter("clinicId", filter.getClinicId());
            query.setParameter("customerId", filter.getCustomerId());
        }

        return query.getResultList();
    }
}
