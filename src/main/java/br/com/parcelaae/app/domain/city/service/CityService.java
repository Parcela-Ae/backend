package br.com.parcelaae.app.domain.city.service;

import br.com.parcelaae.app.domain.city.model.City;
import br.com.parcelaae.app.domain.city.repository.CityRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CityService {

    private final CityRepositoryCustom cityRepositoryCustom;

    public List<City> listAll() {
        var citiesString = cityRepositoryCustom.listAll();
        return citiesString.stream().map(City::new).collect(Collectors.toList());
    }
}
