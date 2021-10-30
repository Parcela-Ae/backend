package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.dto.CustomerDTO;
import br.com.parcelaae.app.dto.FeedbackDTO;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.CustomerRepository;
import br.com.parcelaae.app.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    private final AddressService addressService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ViaCepService viaCepService;

    private final CreditService creditService;

    private final BalanceMovementService balanceMovementService;

    private final FeedbackService feedbackService;

    public Customer findById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(Customer.class, "Não foi encontrada informações para o id do cliente informado"));
    }

    public CustomerDTO toDTO(Customer customer) {
        List<FeedbackDTO> feedbacks = Collections.emptyList();
        if (Objects.nonNull(customer.getFeedbacks()))
            feedbacks = customer.getFeedbacks().stream().map(FeedbackDTO::new).collect(Collectors.toList());

        var lastTransaction = balanceMovementService.getLastTransactionByUserId(customer.getId());
        var lastTransactionDTO = Objects.nonNull(lastTransaction) ? BalanceMovementService.toDTO(lastTransaction) : null;

        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .cpf(customer.getCpf())
                .credit(customer.getCredit())
                .addresses(customer.getAddresses())
                .phones(customer.getPhones())
                .feedbacks(feedbacks)
                .lastTransaction(lastTransactionDTO)
                .build();
    }

    public List<Customer> listAll() {
        return  customerRepository.findAll();
    }

    public User insert(User user) {
        user = userRepository.save(user);
        addressService.saveAll(user.getAddresses());
        creditService.save(new Credit(null, user, 0.0));
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
