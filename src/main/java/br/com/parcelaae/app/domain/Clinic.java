package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.Profile;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Clinic extends User implements Serializable {
    private static final long serialVersionUID = 1L;

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
        this.specialties.addAll(specialties);
        addPerfil(Profile.CLINIC);
    }
}
