package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Usuario;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario insert(Usuario usuario) {
        return repository.save(usuario);
    }

    public void insert(NewUserDTO newUserDTO) {

    }

    /**
     * // TODO: 06/06/2021 Criar método para atualizar somente campos específicos
     */
    public Usuario update(Usuario usuario) {
        return repository.save(usuario);
    }

    public void delete(Integer usuarioId) {
        repository.deleteById(usuarioId);
    }
}
