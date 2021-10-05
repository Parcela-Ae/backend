package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.City;
import br.com.parcelaae.app.domain.State;
import br.com.parcelaae.app.domain.ViaCep;
import br.com.parcelaae.app.services.validation.utils.StateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ViaCepService {

    @Autowired
    private WebClient webClientViaCep;

    @Autowired
    private CityService cityService;

    public ViaCep getCityByZipCode(String zipCode) {
        Mono<ViaCep> viaCepMono = this.webClientViaCep
                .method(HttpMethod.GET)
                .uri("/{cep}/json", zipCode)
                .retrieve()
                .bodyToMono(ViaCep.class);
        return viaCepMono.block();
    }

    public City saveCityIfNotExistAndReturn(String zipCode) {
        ViaCep viaCep = getCityByZipCode(zipCode);
        if (!existCity(viaCep))
            return null;

        var state = State.builder().id(StateUtil.getStateIdByUf(viaCep.getUf())).build();
        var city = City.builder()
                .id(Integer.valueOf(viaCep.getIbge()))
                .name(viaCep.getLocalidade())
                .state(state)
                .build();
        return cityService.save(city);
    }

    private boolean existCity(ViaCep viaCep) {
        return viaCep.getIbge() != null;
    }
}
