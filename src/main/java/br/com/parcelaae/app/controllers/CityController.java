package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.City;
import br.com.parcelaae.app.services.CityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/cities")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> listAll() {
        var cities = cityService.listAll();
        return ResponseEntity.ok(cities);
    }
}
