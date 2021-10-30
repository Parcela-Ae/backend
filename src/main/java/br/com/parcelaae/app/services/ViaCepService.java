package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.ViaCep;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ViaCepService {

    private final WebClient webClientViaCep;

    public ViaCep getCityByZipCode(String zipCode) {
        Mono<ViaCep> viaCepMono = this.webClientViaCep
                .method(HttpMethod.GET)
                .uri("/{cep}/json", zipCode)
                .retrieve()
                .bodyToMono(ViaCep.class);
        return viaCepMono.block();
    }
}
