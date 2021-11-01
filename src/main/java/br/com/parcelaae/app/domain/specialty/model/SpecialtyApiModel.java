package br.com.parcelaae.app.domain.specialty.model;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyApiModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public SpecialtyApiModel(Specialty entity) {
        setId(entity.getId());
        setName(entity.getName());
    }
}
