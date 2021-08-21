package br.com.parcelaae.app.repositories;

import br.com.parcelaae.app.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT feedback FROM Feedback feedback WHERE feedback.customer.id = :customerId")
    List<Feedback> findByCustomerId(@Param("customerId") Integer customerId);
}
