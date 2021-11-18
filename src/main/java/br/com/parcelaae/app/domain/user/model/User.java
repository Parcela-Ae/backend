package br.com.parcelaae.app.domain.user.model;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.credit.model.Credit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Boolean enabled = false;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="PHONES")
    private Set<String> phones = new HashSet<>();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROFILES",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name="profile_id")
    private Set<Integer> profiles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Credit credit;

    protected User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(Profile::toEnum).collect(Collectors.toSet());
    }

    public void addPerfil(Profile profile) {
        profiles.add(profile.getCod());
    }
}
