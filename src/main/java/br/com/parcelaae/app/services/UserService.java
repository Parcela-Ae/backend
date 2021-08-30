package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.dto.UserProfileDTO;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User insert(User user) {
        return repository.save(user);
    }

    /**
     * // TODO: 06/06/2021 Criar método para atualizar somente campos específicos
     */
    public User update(User user) {
        return repository.save(user);
    }

    public void delete(Integer usuarioId) {
        repository.deleteById(usuarioId);
    }

    public UserProfileDTO getUserProfile(UserSS userSS) {
        return new UserProfileDTO(userSS);
    }
}
