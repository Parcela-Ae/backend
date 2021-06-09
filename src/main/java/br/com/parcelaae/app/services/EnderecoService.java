package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Endereco;
import br.com.parcelaae.app.repositories.EnderecoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    public Endereco find(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado para o Id: " + id, Endereco.class.getName()));
    }

    @Transactional
    public Endereco insert(Endereco endereco) {
        endereco.setId(null);
        return repository.save(endereco);
    }

    @Transactional
    public List<Endereco> saveAll(List<Endereco> listaEnderecos) {
        return repository.saveAll(listaEnderecos);
    }

    /**
     * @// TODO: 03/06/2021 Criar método para definir quais campos serão atualizados antes de salvar
     */
    public Endereco update(Endereco novoEndereco) {
        return repository.save(novoEndereco);
    }

    public void delete(Integer id) {
        find(id);
        repository.deleteById(id);
    }
}
