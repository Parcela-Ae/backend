package br.com.parcelaae.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private Integer avaliacao;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
}
