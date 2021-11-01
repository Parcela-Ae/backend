package br.com.parcelaae.app.domain.address.service;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.address.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class AddressService {

    private final AddressRepository repository;

    public Address find(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado para o Id: " + id, Address.class.getName()));
    }

    @Transactional
    public Address insert(Address address) {
        return repository.save(address);
    }

    @Transactional
    public List<Address> saveAll(List<Address> listaAddresses) {
        return repository.saveAll(listaAddresses);
    }

    /**
     * TODO: 03/06/2021 Criar método para definir quais campos serão atualizados antes de salvar
     */
    public Address update(Address newAddress) {
        return repository.save(newAddress);
    }

    public void delete(Integer id) {
        find(id);
        repository.deleteById(id);
    }
}
