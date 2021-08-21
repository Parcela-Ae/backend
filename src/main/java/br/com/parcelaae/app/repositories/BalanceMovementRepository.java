package br.com.parcelaae.app.repositories;

import br.com.parcelaae.app.domain.BalanceMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceMovementRepository extends JpaRepository<BalanceMovement, Integer> {
}
