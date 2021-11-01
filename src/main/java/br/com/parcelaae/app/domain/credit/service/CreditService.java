package br.com.parcelaae.app.domain.credit.service;

import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.repository.CreditRepository;
import br.com.parcelaae.app.domain.credit.repository.CreditCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditService {

    private final CreditRepository creditRepository;

    private final CreditCustomRepository creditCustomRepository;

    public void save(Credit credit) {
        creditRepository.save(credit);
    }

    public Credit findById(Long creditId) {
        return creditRepository.findById(creditId).orElseThrow(IllegalArgumentException::new);
    }

    public Credit findByUserId(Integer userId) {
        return creditCustomRepository.findByUserId(userId);
    }

    public Credit findByCpf(String cpf) {
        return creditCustomRepository.findByCpf(cpf);
    }

    public Credit findByCnpj(String cnpj) {
        return creditCustomRepository.findByCnpj(cnpj);
    }
}
