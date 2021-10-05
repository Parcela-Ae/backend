package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.CustomerRepository;
import br.com.parcelaae.app.repositories.UserRepository;
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

    private List<Customer> customersExpected = new ArrayList<>();

    private NewUserDTO newUserDTO;

    @BeforeEach
    public void setUp() {
        var customer = new Customer();
        customersExpected.addAll(List.of(customer));

        newUserDTO = NewUserDTO.builder()
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
    void shouldConveterNewUserDtoToCustomer() {
        when(passwordEncoder.encode(newUserDTO.getPassword())).thenReturn(ENCRYPTED_PASSWORD);

        var customer = customerService.fromDTO(newUserDTO);

        assertThat(customer.getName()).isEqualTo(newUserDTO.getName());
        assertThat(customer.getEmail()).isEqualTo(newUserDTO.getEmail());
        assertThat(customer.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
        assertThat(customer.getCpf()).isEqualTo(newUserDTO.getCpfOuCnpj());

        assertThat(newUserDTO.getPhone1()).isIn(customer.getPhones());
    }

    @Test
    void shouldConveterNewUserDtoToCustomerWithTwoPhones() {
        newUserDTO.setPhone2("32364455");

        var customer = customerService.fromDTO(newUserDTO);

        assertThat(newUserDTO.getPhone2()).isIn(customer.getPhones());
    }

    @Test
    void shouldConveterNewUserDtoToCustomerWithThreePhones() {
        newUserDTO.setPhone3("32364455");

        var customer = customerService.fromDTO(newUserDTO);

        assertThat(newUserDTO.getPhone3()).isIn(customer.getPhones());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCityIdIsInvalid() {
        try {
            customerService.fromDTO(newUserDTO);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid city id");
        }
    }
}
