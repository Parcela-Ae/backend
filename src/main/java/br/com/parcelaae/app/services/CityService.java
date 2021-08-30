package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.City;
import br.com.parcelaae.app.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository repository;

    @Autowired
    private ViaCepService viaCepService;

    public City getCityById(Integer cityId) {
        return repository.getById(cityId);
    }

    public Optional<City> getOptionalCityById(Integer cityId) {
        return repository.findById(cityId);
    }

    public City save(City city) {
        return repository.save(city);
    }

    public City getCityByZipCode(String zipCode) {
        return repository.findByZipCode(zipCode);
    }

    public boolean isAValidCity(Integer cityId, String zipCode) {
        Optional<City> city = getOptionalCityById(cityId);
        if (city.isEmpty()) {
            return isAValidCityInTheViaCep(zipCode);
        }
        return true;
    }


    private boolean isAValidCityInTheViaCep(String zipCode) {
        return viaCepService.saveCityIfNotExistAndReturn(zipCode) != null;
    }
}
