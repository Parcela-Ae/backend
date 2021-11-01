package br.com.parcelaae.app.domain.clinic.model;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.enums.Profile;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Clinic extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String cnpj;

    @ManyToMany
    @JoinColumn(name = "specialty_id")
    private List<Specialty> specialties = new ArrayList<>();

    public Clinic() {
        super();
        addPerfil(Profile.CLINIC);
    }

    public Clinic(String name, String email, String password, String cnpj) {
        super(name, email, password);
        this.cnpj = cnpj;
        addPerfil(Profile.CLINIC);
    }

    public Clinic(Integer id) {
        setId(id);
    }
}
