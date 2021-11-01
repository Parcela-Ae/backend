package br.com.parcelaae.app.domain.customer.service;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.address.service.AddressService;
import br.com.parcelaae.app.domain.balancemovement.service.BalanceMovementService;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import br.com.parcelaae.app.domain.customer.model.Customer;
import br.com.parcelaae.app.domain.customer.model.CustomerApiResponse;
import br.com.parcelaae.app.domain.customer.repository.CustomerRepository;
import br.com.parcelaae.app.domain.feedback.model.FeedbackApiResponse;
import br.com.parcelaae.app.domain.feedback.service.FeedbackService;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
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

    private final CreditService creditService;

    private final BalanceMovementService balanceMovementService;

    private final FeedbackService feedbackService;

    public Customer findById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(Customer.class, "Não foi encontrada informações para o id do cliente informado"));
    }

    public CustomerApiResponse toDTO(Customer customer) {
        List<FeedbackApiResponse> feedbacks = Collections.emptyList();
        if (Objects.nonNull(customer.getFeedbacks()))
            feedbacks = customer.getFeedbacks().stream().map(FeedbackApiResponse::new).collect(Collectors.toList());

        var lastTransaction = balanceMovementService.getLastTransactionByUserId(customer.getId());
        var lastTransactionDTO = Objects.nonNull(lastTransaction) ? BalanceMovementService.toDTO(lastTransaction) : null;

        return CustomerApiResponse.builder()
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

    public Customer fromDTO(UserApiRequest userApiRequest) {
        var customer = new Customer();
        customer.setName(userApiRequest.getName());
        customer.setEmail(userApiRequest.getEmail());
        customer.setCpf(userApiRequest.getCpfOuCnpj());
        customer.setPassword(passwordEncoder.encode(userApiRequest.getPassword()));

        var address = Address.builder()
                .publicArea(userApiRequest.getPublicArea())
                .number(userApiRequest.getNumber())
                .complement(userApiRequest.getComplement())
                .neighborhood(userApiRequest.getNeighborhood())
                .zipCode(userApiRequest.getZipCode())
                .user(customer)
                .city(userApiRequest.getCity())
                .state(userApiRequest.getState())
                .build();

        customer.getAddresses().add(address);
        customer.getPhones().add(userApiRequest.getPhone1());
        if (userApiRequest.getPhone2()!=null) {
            customer.getPhones().add(userApiRequest.getPhone2());
        }
        if (userApiRequest.getPhone3()!=null) {
            customer.getPhones().add(userApiRequest.getPhone3());
        }
        return customer;
    }
}
