package br.com.parcelaae.app.domain.city.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class City {

    public final String name;
}
