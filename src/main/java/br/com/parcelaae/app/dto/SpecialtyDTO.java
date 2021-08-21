package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SpecialtyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public SpecialtyDTO(Specialty entity) {
        setId(entity.getId());
        setName(entity.getName());
    }
}
