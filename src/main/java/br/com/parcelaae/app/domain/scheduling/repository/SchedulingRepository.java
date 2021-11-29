package br.com.parcelaae.app.domain.scheduling.repository;

import br.com.parcelaae.app.domain.scheduling.model.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    @Query("SELECT scheduling FROM Scheduling scheduling WHERE scheduling.customer.id = :customerId")
    List<Scheduling> findAllByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT scheduling FROM Scheduling scheduling WHERE scheduling.clinic.id = :clinicId")
    List<Scheduling> findAllByClinicId(Integer clinicId);
}
