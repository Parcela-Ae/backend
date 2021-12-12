package br.com.parcelaae.app.domain.clinic.model;

import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Clinic extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String cnpj;

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
