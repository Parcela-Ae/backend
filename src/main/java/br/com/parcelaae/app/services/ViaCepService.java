package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.ViaCep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ViaCepService {

    @Autowired
    private WebClient webClientViaCep;

    public ViaCep getCityByZipCode(String zipCode) {
        Mono<ViaCep> viaCepMono = this.webClientViaCep
                .method(HttpMethod.GET)
                .uri("/{cep}/json", zipCode)
                .retrieve()
                .bodyToMono(ViaCep.class);
        return viaCepMono.block();
    }
}
