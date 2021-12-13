package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.city.model.City;
import br.com.parcelaae.app.domain.city.service.CityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class CityControllerTest {

    private CityController cityController;

    @Mock
    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityController = new CityController(cityService);
    }

    @Test
    void shouldListAllCities() {
        var citiesExpected = Collections.singletonList(City.builder().build());

        when(cityService.listAll()).thenReturn(citiesExpected);

        var response = cityController.listAll();

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(citiesExpected);
    }
}