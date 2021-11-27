package br.com.parcelaae.app.domain.customer;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.customer.model.Customer;
import br.com.parcelaae.app.domain.address.service.AddressService;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.customer.repository.CustomerRepository;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import br.com.parcelaae.app.domain.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final String ENCRYPTED_PASSWORD = "******";

    private static final String DECRYPTED_PASSWORD = "123456";

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private CreditService creditService;

    private List<Customer> customersExpected = new ArrayList<>();

    private UserApiRequest userApiRequest;

    @BeforeEach
    public void setUp() {
        var customer = new Customer();
        customersExpected.addAll(List.of(customer));

        userApiRequest = UserApiRequest.builder()
                .name("John Wick")
                .email("john@wick.com")
                .cpfOuCnpj("00011122233")
                .password(DECRYPTED_PASSWORD)
                .cityId(2610707)
                .publicArea("Rua das Acácias")
                .number("345")
                .complement("Apto 302")
                .zipCode("38466837")
                .phone1("996611122")
                .build();
    }

    @Test
    void shouldReturnListOfTheAllCustomer() {
        when(customerRepository.findAll()).thenReturn(customersExpected);

        var customersActual = customerService.listAll();

        assertEquals(customersExpected, customersActual);
    }

    @Test
    void shouldSaveANewCustomer() {
        var address = new Address();
        var customerToSave = new Customer();
        customerToSave.getAddresses().add(address);
        var customerExpected = Customer.builder().cpf("123456").build();

        when(userRepository.save(customerToSave)).thenReturn(customerExpected);

        var newCustomerActual = customerService.insert(customerToSave);

        assertThat(newCustomerActual).isNotNull();
        verify(addressService, times(1)).saveAll(customerExpected.getAddresses());
    }

    @Test
    void shouldConverterNewUserDtoToCustomer() {
        when(passwordEncoder.encode(userApiRequest.getPassword())).thenReturn(ENCRYPTED_PASSWORD);

        var customer = customerService.fromDTO(userApiRequest);

        assertThat(customer.getName()).isEqualTo(userApiRequest.getName());
        assertThat(customer.getEmail()).isEqualTo(userApiRequest.getEmail());
        assertThat(customer.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
        assertThat(customer.getCpf()).isEqualTo(userApiRequest.getCpfOuCnpj());

        assertThat(userApiRequest.getPhone1()).isIn(customer.getPhones());
    }

    @Test
    void shouldConverterNewUserDtoToCustomerWithTwoPhones() {
        userApiRequest.setPhone2("32364455");

        var customer = customerService.fromDTO(userApiRequest);

        assertThat(userApiRequest.getPhone2()).isIn(customer.getPhones());
    }

    @Test
    void shouldConverterNewUserDtoToCustomerWithThreePhones() {
        userApiRequest.setPhone3("32364455");

        var customer = customerService.fromDTO(userApiRequest);

        assertThat(userApiRequest.getPhone3()).isIn(customer.getPhones());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCityIdIsInvalid() {
        try {
            customerService.fromDTO(userApiRequest);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid city id");
        }
    }
}
