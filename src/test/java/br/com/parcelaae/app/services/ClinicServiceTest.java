package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.domain.Specialty;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.ClinicRepository;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.repositories.custom.ClinicCustomRepository;
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
    private ClinicCustomRepository clinicCustomRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private NewUserDTO newUserDTO;

    @BeforeEach
    public void setUp() {
        newUserDTO = NewUserDTO.builder()
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
    }

    @Test
    void shouldFindAListOfClinics() {
        var filter = ClinicFilter.builder().build();
        List<Clinic> clinics = List.of(Clinic.builder().build());

        when(clinicCustomRepository.find(filter)).thenReturn(clinics);

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
    void shouldConveterNewUserDtoToClinic() {
        when(passwordEncoder.encode(newUserDTO.getPassword())).thenReturn(ENCRYPTED_PASSWORD);

        var customer = clinicService.fromDTO(newUserDTO);

        assertThat(customer.getName()).isEqualTo(newUserDTO.getName());
        assertThat(customer.getEmail()).isEqualTo(newUserDTO.getEmail());
        assertThat(customer.getPassword()).isEqualTo(ENCRYPTED_PASSWORD);
        assertThat(customer.getCnpj()).isEqualTo(newUserDTO.getCpfOuCnpj());

        assertThat(newUserDTO.getPhone1()).isIn(customer.getPhones());
    }

    @Test
    void shouldConveterNewUserDtoToCustomerWithTwoPhones() {
        newUserDTO.setPhone2("32364455");

        var customer = clinicService.fromDTO(newUserDTO);

        assertThat(newUserDTO.getPhone2()).isIn(customer.getPhones());
    }

    @Test
    void shouldConveterNewUserDtoToCustomerWithThreePhones() {
        newUserDTO.setPhone3("32364455");

        var customer = clinicService.fromDTO(newUserDTO);

        assertThat(newUserDTO.getPhone3()).isIn(customer.getPhones());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCityIdIsInvalid() {
        try {
            clinicService.fromDTO(newUserDTO);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid city id");
        }
    }
}
