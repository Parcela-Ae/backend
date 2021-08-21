package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.Perfil;
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
public class Clinica extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cnpj;

    @ManyToMany
    @JoinColumn(name = "especialidade_id")
    private List<Especialidade> especialidades = new ArrayList<>();

    public Clinica() {
        super();
        addPerfil(Perfil.CLINICA);
    }

    public Clinica(String nome, String email, String senha, String cnpj) {
        super(nome, email, senha);
        this.cnpj = cnpj;
        this.especialidades.addAll(especialidades);
        addPerfil(Perfil.CLINICA);
    }
}
