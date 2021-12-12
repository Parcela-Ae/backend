package br.com.parcelaae.app.domain.credit.service;

import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class CreditService {

    private final CreditRepository creditRepository;

    public void save(Credit credit) {
        creditRepository.save(credit);
    }

    public Credit findById(Long creditId) {
        return creditRepository.findById(creditId).orElseThrow(IllegalArgumentException::new);
    }

    public Credit findByUserId(Integer userId) {
        return creditRepository.findByUserId(userId);
    }

    public Credit findByCpf(String cpf) {
        return creditRepository.findByCpf(cpf);
    }

    public Credit findByCnpj(String cnpj) {
        return creditRepository.findByCnpj(cnpj);
    }
}
