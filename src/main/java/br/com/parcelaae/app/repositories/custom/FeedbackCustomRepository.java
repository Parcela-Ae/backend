package br.com.parcelaae.app.repositories.custom;

import br.com.parcelaae.app.controllers.queryfilter.FeedbackFilter;
import br.com.parcelaae.app.domain.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FeedbackCustomRepository {

    private final EntityManager em;

    public List<Feedback> find(@NotNull FeedbackFilter filter) {
        var sql = new StringBuilder();

        sql.append(" SELECT feedback ");
        sql.append(" FROM Feedback feedback ");

        if (filter.getCustomerId() != null && filter.getClinicId() == null) {
            sql.append(" WHERE feedback.customer.id LIKE :customerId");
        }
        if (filter.getClinicId() != null && filter.getCustomerId() == null) {
            sql.append(" WHERE feedback.clinic.id LIKE :clinicId");
        }

        var query = em.createQuery(sql.toString(), Feedback.class);

        if (filter.getCustomerId() != null && filter.getClinicId() == null) {
            query.setParameter("customerId", filter.getCustomerId());
        }
        if (filter.getClinicId() != null && filter.getCustomerId() == null) {
            query.setParameter("clinicId", filter.getClinicId());
        }
        return query.getResultList();
    }
}
