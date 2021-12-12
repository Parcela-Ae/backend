package br.com.parcelaae.app.domain.clinic.repository;

import br.com.parcelaae.app.domain.clinic.model.ClinicSpecialty;
import br.com.parcelaae.app.domain.clinic.model.ClinicSpecialtyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicSpecialtyRepository extends JpaRepository<ClinicSpecialty, ClinicSpecialtyId> {

    @Query("SELECT cs FROM ClinicSpecialty cs WHERE cs.clinic.id = :clinicId")
    List<ClinicSpecialty> listAllSpecialtiesByClinicId(@Param("clinicId") Integer clinicId);
}
