package br.com.parcelaae.app.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cpf;

    @OneToMany(mappedBy = "cliente")
    private List<Feedback> feedbacks = new ArrayList<>();

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
