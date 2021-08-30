package br.com.parcelaae.app.repositories;

import br.com.parcelaae.app.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    @Query("SELECT state FROM State state WHERE state.uf = :uf")
    State findByUf(@Param("uf") String uf);
}
