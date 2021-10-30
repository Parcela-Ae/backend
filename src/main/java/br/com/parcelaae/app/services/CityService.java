package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.City;
import br.com.parcelaae.app.repositories.custom.CityCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CityService {

    private final CityCustomRepository cityCustomRepository;

    public List<City> listAll() {
        var citiesString = cityCustomRepository.listAll();
        return citiesString.stream().map(City::new).collect(Collectors.toList());
    }
}
