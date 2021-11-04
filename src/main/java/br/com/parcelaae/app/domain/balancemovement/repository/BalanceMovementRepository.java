package br.com.parcelaae.app.domain.balancemovement.repository;

import br.com.parcelaae.app.domain.balancemovement.model.BalanceMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceMovementRepository extends JpaRepository<BalanceMovement, Long> {

    @Query("SELECT transaction FROM BalanceMovement transaction WHERE transaction.origin.user.id = :userId OR transaction.destination.user.id = :userId ORDER BY transaction.id DESC")
    List<BalanceMovement> findByUserId(@Param("userId") Integer userId);
}
