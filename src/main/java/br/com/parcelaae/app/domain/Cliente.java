package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.Perfil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
