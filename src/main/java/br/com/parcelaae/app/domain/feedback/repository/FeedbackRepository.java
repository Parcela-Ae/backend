package br.com.parcelaae.app.domain.feedback.repository;

import br.com.parcelaae.app.domain.feedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, FeedbackRepositoryCustom {

    @Query("SELECT feedback FROM Feedback feedback WHERE feedback.customer.id = :customerId")
    List<Feedback> findByCustomerId(@Param("customerId") Integer customerId);
}
