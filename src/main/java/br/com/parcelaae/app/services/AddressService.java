package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.repositories.AddressRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address find(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado para o Id: " + id, Address.class.getName()));
    }

    @Transactional
    public Address insert(Address address) {
        address.setId(null);
        return repository.save(address);
    }

    @Transactional
    public List<Address> saveAll(List<Address> listaAddresses) {
        return repository.saveAll(listaAddresses);
    }

    /**
     * @// TODO: 03/06/2021 Criar método para definir quais campos serão atualizados antes de salvar
     */
    public Address update(Address novoAddress) {
        return repository.save(novoAddress);
    }

    public void delete(Integer id) {
        find(id);
        repository.deleteById(id);
    }
}