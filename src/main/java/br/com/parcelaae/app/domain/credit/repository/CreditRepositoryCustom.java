package br.com.parcelaae.app.domain.credit.repository;

import br.com.parcelaae.app.domain.credit.model.Credit;

import javax.validation.constraints.NotNull;

public interface CreditRepositoryCustom {

    Credit findByUserId(Integer userId);

    Credit findByCpf(@NotNull String cpf);

    Credit findByCnpj(String cnpj);
}
