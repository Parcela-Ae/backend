package br.com.parcelaae.app.domain.email.repository;

import br.com.parcelaae.app.domain.email.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
}
