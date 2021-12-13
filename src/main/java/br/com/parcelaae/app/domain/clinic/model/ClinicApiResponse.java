package br.com.parcelaae.app.domain.clinic.model;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import lombok.*;

import java.util.ArrayList;
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
    private Credit credit;
    private List<SpecialtyApiModel> specialties = new ArrayList<>();
    private List<Address> addresses;
    private Set<String> phones;
}
