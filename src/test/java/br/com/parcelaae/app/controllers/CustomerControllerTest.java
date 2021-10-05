package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    public static final String CUSTOMER_NAME = "Ok Doutor";
    public static final String URI_EXPECTED = "http://localhost/customers/1";

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        var request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(-1);
        request.setRequestURI("/customers");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    public void setDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldFindWithFilter() {
        var customer = Customer.builder().name(CUSTOMER_NAME).build();
        var customers = List.of(customer);

        when(customerService.listAll()).thenReturn(customers);

        var response = customerController.findAll();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldInsertANewCustomer() {
        var newUserDTO = NewUserDTO.builder()
                .name(CUSTOMER_NAME)
                .build();
        var newCustomer = Customer.builder()
                .id(1)
                .name(CUSTOMER_NAME)
                .build();

        when(customerService.fromDTO(newUserDTO)).thenReturn(newCustomer);

        var response = customerController.insert(newUserDTO);
        var uriGenerated = requireNonNull(response.getHeaders().get("Location")).get(0);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().containsKey("Location")).isTrue();
        assertThat(uriGenerated).isEqualTo(URI_EXPECTED);
    }
}
