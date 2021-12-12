package br.com.parcelaae.app.domain.clinic;

import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.repository.ClinicSpecialtyRepository;
import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.address.service.AddressService;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.clinic.repository.ClinicRepository;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
import br.com.parcelaae.app.domain.clinic.service.ClinicService;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ClinicServiceTest {

    private static final String ENCRYPTED_PASSWORD = "******";

    private static final String DECRYPTED_PASSWORD = "123456";

    @InjectMocks
    private ClinicService clinicService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClinicRepository clinicRepository;

    @Mock
    private ClinicSpecialtyRepository clinicSpecialtyRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private CreditService creditService;

    private UserApiRequest userApiRequest;

    @BeforeEach
    public void setUp() {
        userApiRequest = UserApiRequest.builder()
                .name("John Wick")
                .email("john@wick.com")
                .cpfOuCnpj("00011122233")
                .specialties(List.of(Specialty.builder().build()))
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
    void shouldInsertANewUser() {
        var addresses = List.of(Address.builder().build(), Address.builder().build());
        var userToSave = Clinic.builder().build();

        var userExpected = Clinic.builder().id(1).addresses(addresses).build();

        when(userRepository.save(userToSave)).thenReturn(userExpected);

        var userActual = clinicService.insert(userToSave);

        assertThat(userActual).isNotNull();
        assertThat(userActual.getId()).isEqualTo(userExpected.getId());
        verify(addressService, times(1)).saveAll(userExpected.getAddresses());
        verify(clinicSpecialtyRepository, times(1)).saveAll(any());
    }

    @Test
    void shouldFindAListOfClinics() {
        var filter = ClinicRestFilter.builder().build();
        List<Clinic> clinics = List.of(Clinic.builder().build());

        when(clinicRepository.find(filter)).thenReturn(clinics);

        var clinicsActual = clinicService.find(filter);

        assertThat(clinicsActual).isNotEmpty();
    }

    @Test
    void shouldListAllClinics() {
        List<Clinic> clinics = List.of(Clinic.builder().build());

        when(clinicRepository.findAll()).thenReturn(clinics);

        var clinicsActual = clinicService.listAll();

        assertThat(clinicsActual).isNotEmpty();
    }

    @Test
    void shouldUpdateTheUser() {
        var userToUpdate = Clinic.builder().build();
        var userExpected = Clinic.builder().build();

        when(userRepository.save(userToUpdate)).thenReturn(userExpected);

        var userActual = clinicService.update(userToUpdate);

        assertThat(userActual).isEqualTo(userExpected);
    }

    @Test
    void shouldDeleteTheUser() {
        var userId = 1;

        doNothing().when(userRepository).deleteById(userId);

        clinicService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void shouldConverterNewUserDtoToClinic() {
        when(passwordEncoder.encode(userApiRequest.getPassword())).thenReturn(ENCRYPTED_PASSWORD);

        var customer = clinicService.fromDTO(userApiRequest);

        assertThat(customer.getName()).isEqualTo(userApiRequest.getName());
        assertThat(customer.getEmail()).isEqualTo(userApiRequest.getEmail());
        assertThat(customer.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
        assertThat(customer.getCnpj()).isEqualTo(userApiRequest.getCpfOuCnpj());

        assertThat(userApiRequest.getPhone1()).isIn(customer.getPhones());
    }

    @Test
    void shouldConverterNewUserDtoToCustomerWithTwoPhones() {
        userApiRequest.setPhone2("32364455");

        var customer = clinicService.fromDTO(userApiRequest);

        assertThat(userApiRequest.getPhone2()).isIn(customer.getPhones());
    }

    @Test
    void shouldConverterNewUserDtoToCustomerWithThreePhones() {
        userApiRequest.setPhone3("32364455");

        var customer = clinicService.fromDTO(userApiRequest);

        assertThat(userApiRequest.getPhone3()).isIn(customer.getPhones());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCityIdIsInvalid() {
        try {
            clinicService.fromDTO(userApiRequest);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid city id");
        }
    }
}
