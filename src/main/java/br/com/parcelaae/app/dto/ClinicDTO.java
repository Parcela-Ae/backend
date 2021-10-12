package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Specialty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClinicDTO {

    private Integer id;
    private String name;
    private String cnpj;
    private List<Specialty> specialties;
    private List<Address> addresses;
    private Set<String> phones;
}
