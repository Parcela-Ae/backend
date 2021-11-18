package br.com.parcelaae.app.domain.security.model;

import br.com.parcelaae.app.domain.user.model.Profile;
import br.com.parcelaae.app.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Getter
    private Integer id;
    @Getter
    private String email;
    private String password;
    @Getter
    private String name;
    @Getter
    @Setter
    private String typeUser;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean enabled;

    public UserSS(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.authorities = user.getProfiles().stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
        this.enabled = user.getEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean hasRole(Profile profile) {
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
    }
}
