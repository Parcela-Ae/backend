package br.com.parcelaae.app.infrastructure.client.viacep.impl;

import br.com.parcelaae.app.infrastructure.client.viacep.ViaCepClient;
import br.com.parcelaae.app.infrastructure.client.viacep.exception.ViaCepClientFailedCallException;
import br.com.parcelaae.app.infrastructure.client.viacep.model.ViaCepClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
@Component
public class ViaCepClientImpl implements ViaCepClient {

    public static final String ERROR_AO_CONSULTAR_API_VIA_CEP = "Error ao consultar a API do ViaCep";
    private final WebClient webClientViaCep;

    public ViaCepClientResponse getCityByZipCode(String zipCode) {
        Optional<ViaCepClientResponse> viaCepClientResponse;

        try {
            Mono<ViaCepClientResponse> viaCepMono = this.webClientViaCep
                    .method(HttpMethod.GET)
                    .uri("/{cep}/json", zipCode)
                    .retrieve()
                    .bodyToMono(ViaCepClientResponse.class);

            viaCepClientResponse = ofNullable(viaCepMono.block());

        } catch (Exception viaCepClientFailedCallException) {
            log.error(ERROR_AO_CONSULTAR_API_VIA_CEP);
            throw new ViaCepClientFailedCallException(viaCepClientFailedCallException.getMessage());
        }

        return viaCepClientResponse.orElse(null);
    }
}
