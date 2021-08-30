package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static br.com.parcelaae.app.security.SecurityUtil.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(email);

        var userSS = new UserSS(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getProfiles());
        userSS.setTypeUser(getTypeUser(userSS));
        return userSS;
    }
}
