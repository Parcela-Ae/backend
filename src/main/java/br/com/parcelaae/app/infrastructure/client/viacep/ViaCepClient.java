package br.com.parcelaae.app.infrastructure.client.viacep;

import br.com.parcelaae.app.infrastructure.client.viacep.model.ViaCepClientResponse;

public interface ViaCepClient {
    ViaCepClientResponse getCityByZipCode(String zipCode);
}
