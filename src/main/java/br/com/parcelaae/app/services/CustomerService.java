package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.*;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.CustomerRepository;
import br.com.parcelaae.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Customer> listAll() {
        return  customerRepository.findAll();
    }

    public User insert(User user) {
        user.setId(null);
        user = userRepository.save(user);
        addressService.saveAll(user.getAddresses());
        return user;
    }

    public Customer fromDTO(NewUserDTO newUserDTO) {
        var cliente = new Customer();
        cliente.setName(newUserDTO.getName());
        cliente.setEmail(newUserDTO.getEmail());
        cliente.setCpf(newUserDTO.getCpfOuCnpj());
        cliente.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        var cid = new City(newUserDTO.getCityId(), null, null);
        var end = Address.builder()
                .publicArea(newUserDTO.getPublicArea())
                .number(newUserDTO.getNumber())
                .complement(newUserDTO.getComplement())
                .neighborhood(newUserDTO.getNeighborhood())
                .zipCode(newUserDTO.getZipCode())
                .user(cliente)
                .city(cid)
                .build();

        cliente.getAddresses().add(end);
        cliente.getPhones().add(newUserDTO.getPhone1());
        if (newUserDTO.getPhone2()!=null) {
            cliente.getPhones().add(newUserDTO.getPhone2());
        }
        if (newUserDTO.getPhone3()!=null) {
            cliente.getPhones().add(newUserDTO.getPhone3());
        }
        return cliente;
    }
}
