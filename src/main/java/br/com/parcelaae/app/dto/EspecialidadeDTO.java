package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Especialidade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EspecialidadeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EspecialidadeDTO(Especialidade entity) {
        setId(entity.getId());
        setNome(entity.getNome());
    }
}
