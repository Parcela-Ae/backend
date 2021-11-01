package br.com.parcelaae.app.domain.specialty.repository;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
}
