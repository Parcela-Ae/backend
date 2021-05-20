package br.com.parcelaae.app.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clinica extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cnpj;

    @ManyToMany
    @JoinColumn(name = "especialidade_id")
    private List<Especialidade> especialidades = new ArrayList<>();

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }
}
