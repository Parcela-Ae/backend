package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.repositories.CreditRepository;
import br.com.parcelaae.app.repositories.custom.CreditCustomRepository;
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
