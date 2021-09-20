package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.City;
import br.com.parcelaae.app.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ViaCepService viaCepService;

    @Test
    void shouldGetCityById() {
        var cityId = 1;
        var city = City.builder().build();

        when(cityRepository.getById(cityId)).thenReturn(city);

        var cityActual = cityService.getCityById(cityId);

        assertThat(cityActual).isNotNull();
    }

    @Test
    void shouldGetOptionalCityById() {
        var cityId = 1;
        var city = Optional.ofNullable(City.builder().build());

        when(cityRepository.findById(cityId)).thenReturn(city);

        var cityActual = cityService.getOptionalCityById(cityId);

        assertThat(cityActual).isPresent();
    }

    @Test
    void shouldSaveACity() {
        var city = City.builder().build();
        var cityExpected = City.builder().id(1).build();

        when(cityRepository.save(city)).thenReturn(cityExpected);

        var cityActual = cityService.save(city);

        assertThat(cityActual).isNotNull();
        assertThat(cityActual.getId()).isEqualTo(cityExpected.getId());
    }

    @Test
    void shouldGetCityByZipCode() {
        var zipCode = "123456";
        var city = City.builder().build();

        when(cityRepository.findByZipCode(zipCode)).thenReturn(city);

        var cityActual = cityService.getCityByZipCode(zipCode);

        assertThat(cityActual).isNotNull();
    }

    @Test
    void shouldReturnTrueWhenTheCityIdAndZipCodeAreValid() {
        var cityId = 1;
        var zipCode = "123456";
        var cityOptional = Optional.ofNullable(City.builder().build());
        var city = City.builder().build();

        when(cityRepository.findById(cityId)).thenReturn(cityOptional);
        //when(viaCepService.saveCityIfNotExistAndReturn(zipCode)).thenReturn(city);

        var isAValidCityInTheViaCep = cityService.isAValidCity(cityId, zipCode);

        assertThat(isAValidCityInTheViaCep).isTrue();
    }

    @Test
    void shouldReturnFalseWhenTheCityIdAndZipCodeAreNotValid() {
        var cityId = 1;
        var zipCode = "123456";
        var city = City.builder().build();

        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());
        when(viaCepService.saveCityIfNotExistAndReturn(zipCode)).thenReturn(null);

        var isAValidCityInTheViaCep = cityService.isAValidCity(cityId, zipCode);

        assertThat(isAValidCityInTheViaCep).isFalse();
        verify(viaCepService, times(1)).saveCityIfNotExistAndReturn(zipCode);
    }
}
