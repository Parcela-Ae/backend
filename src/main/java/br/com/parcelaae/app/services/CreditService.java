package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.repositories.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public void save(Credit credit) {
        creditRepository.save(credit);
    }

    public Credit findById(Long creditId) {
        return creditRepository.findById(creditId).orElseThrow(IllegalArgumentException::new);
    }
}
