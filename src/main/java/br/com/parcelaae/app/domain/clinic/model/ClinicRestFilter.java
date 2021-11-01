package br.com.parcelaae.app.domain.clinic.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClinicRestFilter {

    private String name;
    private String specialty;
    private String city;
}
