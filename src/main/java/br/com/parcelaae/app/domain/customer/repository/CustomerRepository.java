package br.com.parcelaae.app.domain.customer.repository;

import br.com.parcelaae.app.domain.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
