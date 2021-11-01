package br.com.parcelaae.app.domain.clinic.model;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.specialty.model.Specialty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClinicApiResponse {

    private Integer id;
    private String name;
    private String cnpj;
    private List<Specialty> specialties;
    private List<Address> addresses;
    private Set<String> phones;
}
