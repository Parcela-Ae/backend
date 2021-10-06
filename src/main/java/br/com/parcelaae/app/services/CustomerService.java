package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.domain.User;
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

    @Autowired
    private ViaCepService viaCepService;

    public List<Customer> listAll() {
        return  customerRepository.findAll();
    }

    public User insert(User user) {
        user = userRepository.save(user);
        addressService.saveAll(user.getAddresses());
        return user;
    }

    public Customer fromDTO(NewUserDTO newUserDTO) {
        var customer = new Customer();
        customer.setName(newUserDTO.getName());
        customer.setEmail(newUserDTO.getEmail());
        customer.setCpf(newUserDTO.getCpfOuCnpj());
        customer.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        var address = Address.builder()
                .publicArea(newUserDTO.getPublicArea())
                .number(newUserDTO.getNumber())
                .complement(newUserDTO.getComplement())
                .neighborhood(newUserDTO.getNeighborhood())
                .zipCode(newUserDTO.getZipCode())
                .user(customer)
                .city(newUserDTO.getCity())
                .state(newUserDTO.getState())
                .build();

        customer.getAddresses().add(address);
        customer.getPhones().add(newUserDTO.getPhone1());
        if (newUserDTO.getPhone2()!=null) {
            customer.getPhones().add(newUserDTO.getPhone2());
        }
        if (newUserDTO.getPhone3()!=null) {
            customer.getPhones().add(newUserDTO.getPhone3());
        }
        return customer;
    }
}
