package br.com.parcelaae.app.domain.address.repository;

import br.com.parcelaae.app.domain.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
