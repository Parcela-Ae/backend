package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;

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
