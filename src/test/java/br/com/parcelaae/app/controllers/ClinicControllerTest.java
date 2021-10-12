package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.dto.ClinicDTO;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.ClinicService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClinicControllerTest {

    public static final String CLINIC_NAME = "Ok Doutor";
    public static final String URI_EXPECTED = "http://localhost/clinics/1";

    @InjectMocks
    private ClinicController clinicController;

    @Mock
    private ClinicService clinicService;

    @BeforeEach
    public void setUp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(-1);
        request.setRequestURI("/clinics");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    public void setDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldFindWithFilter() {
        var filter = ClinicFilter.builder().name(CLINIC_NAME).build();
        var clinic = Clinic.builder().name(CLINIC_NAME).build();
        var clinics = List.of(clinic);
        var clinicDTO = new ClinicDTO();
        var clinicsDTOExpected= List.of(clinicDTO);
        BeanUtils.copyProperties(clinic, clinicDTO);

        when(clinicService.find(filter)).thenReturn(clinics);
        when(clinicService.fromEntity(clinic)).thenReturn(clinicDTO);

        var response = clinicController.find(filter);
        var responseBody = response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBody).usingRecursiveFieldByFieldElementComparator().isEqualTo(clinicsDTOExpected);
    }

    @Test
    void shouldInsertANewClinic() {
        var newUserDTO = NewUserDTO.builder()
                .name(CLINIC_NAME)
                .build();
        var newClinic = Clinic.builder()
                .id(1)
                .name(CLINIC_NAME)
                .build();

        when(clinicService.fromDTO(newUserDTO)).thenReturn(newClinic);

        var response = clinicController.insert(newUserDTO);
        var uriGenerated = requireNonNull(response.getHeaders().get("Location")).get(0);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().containsKey("Location")).isTrue();
        assertThat(uriGenerated).isEqualTo(URI_EXPECTED);
    }
}
