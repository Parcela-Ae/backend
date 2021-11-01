package br.com.parcelaae.app.domain.clinic.repository;

import br.com.parcelaae.app.domain.clinic.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
}
