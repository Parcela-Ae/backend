package br.com.parcelaae.app.domain.customer.model;

import br.com.parcelaae.app.domain.feedback.model.Feedback;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.enums.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "customer")
    private List<Feedback> feedbacks = new ArrayList<>();

    public Customer() {
        addPerfil(Profile.CUSTOMER);
    }

    public Customer(String name, String email, String password, String cpf) {
        super(name, email, password);
        this.cpf = cpf;
        addPerfil(Profile.CUSTOMER);
    }

    public Customer(Integer id) {
        setId(id);
    }
}
