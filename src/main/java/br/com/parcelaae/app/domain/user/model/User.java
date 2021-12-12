package br.com.parcelaae.app.domain.user.model;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.clinic.model.ClinicSpecialty;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.domain.specialty.model.Specialty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
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

    @OneToMany(
            mappedBy = "clinic",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ClinicSpecialty> specialties = new ArrayList<>();

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

    public void addSpecialty(Specialty specialty) {
        var clinicSpecialty = new ClinicSpecialty(this, specialty);
        specialties.add(clinicSpecialty);
    }

    public void removeSpecialty(Specialty specialty) {
        for (Iterator<ClinicSpecialty> iterator = specialties.iterator();
             iterator.hasNext(); ) {

            var clinicSpecialty = iterator.next();

            if (clinicSpecialty.getClinic().equals(this) &&
                    clinicSpecialty.getSpecialty().equals(specialty)) {
                iterator.remove();
                clinicSpecialty.setSpecialty(null);
                clinicSpecialty.setClinic(null);
            }
        }
    }
}
