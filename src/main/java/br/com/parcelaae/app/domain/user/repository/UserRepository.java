package br.com.parcelaae.app.domain.user.repository;

import br.com.parcelaae.app.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    User findByEmail(String email);
}
