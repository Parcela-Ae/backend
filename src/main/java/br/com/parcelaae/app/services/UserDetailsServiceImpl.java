package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Usuario;
import br.com.parcelaae.app.repositories.UsuarioRepository;
import br.com.parcelaae.app.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email);

        return new UserSS(user.getId(), user.getEmail(), user.getSenha(), user.getPerfis());
    }
}
