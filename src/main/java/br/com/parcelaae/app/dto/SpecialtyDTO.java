package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Specialty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
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
