package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Especialidade;
import lombok.Data;

import java.io.Serializable;

@Data
public class EspecialidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EspecialidadeDTO() {
    }

    public EspecialidadeDTO(Especialidade entity) {
        setId(entity.getId());
        setNome(entity.getNome());
    }
}
