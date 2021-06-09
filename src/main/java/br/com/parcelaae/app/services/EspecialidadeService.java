package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Especialidade;
import br.com.parcelaae.app.dto.EspecialidadeDTO;
import br.com.parcelaae.app.repositories.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repository;

    public Especialidade insert(Especialidade especialidade) {
        return repository.save(especialidade);
    }

    public List<Especialidade> listAll() {
        return repository.findAll();
    }

    public Especialidade fromDTO(EspecialidadeDTO especialidadeDTO) {
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeDTO.getNome());
        return  especialidade;
    }
}
