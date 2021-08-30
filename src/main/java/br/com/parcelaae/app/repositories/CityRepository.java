package br.com.parcelaae.app.repositories;

import br.com.parcelaae.app.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT city FROM City city INNER JOIN Address address ON city.id = address.city.id WHERE city.id = address.city.id AND address.zipCode = :zipCode")
    City findByZipCode(@Param("zipCode") String zipCode);
}
