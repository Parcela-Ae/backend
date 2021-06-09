package br.com.parcelaae.app.repositories;

import br.com.parcelaae.app.domain.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Integer> {
}
