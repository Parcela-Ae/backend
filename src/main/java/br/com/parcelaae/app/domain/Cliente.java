package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Cliente extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;

    @OneToMany(mappedBy = "cliente")
    private List<Feedback> feedbacks = new ArrayList<>();

    public Cliente() {
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(String nome, String email, String senha, String cpf) {
        super(nome, email, senha);
        this.cpf = cpf;
        addPerfil(Perfil.CLIENTE);
    }
}
