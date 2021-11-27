package br.com.parcelaae.app.domain.credit.repository;

import br.com.parcelaae.app.domain.credit.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>, CreditRepositoryCustom {
}
