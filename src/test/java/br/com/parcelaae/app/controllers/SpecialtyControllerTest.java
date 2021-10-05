package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Specialty;
import br.com.parcelaae.app.dto.SpecialtyDTO;
import br.com.parcelaae.app.services.SpecialtyService;
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

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {

    public static final String URI_EXPECTED = "http://localhost/specialties/1";

    @InjectMocks
    private SpecialtyController specialtyController;

    @Mock
    private SpecialtyService specialtyService;

    @BeforeEach
    void setUp() {
        var request = new MockHttpServletRequest();
        request.setScheme("http");
        request.setServerName("localhost");
        request.setServerPort(-1);
        request.setRequestURI("/specialties");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    public void setDown() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void shouldListAll() {
        var specialties = singletonList(Specialty.builder().id(1).name("Cardiologia").build());

        when(specialtyService.listAll()).thenReturn(specialties);

        var response = specialtyController.listAll();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldInsert() {
        var specialtyDTO = SpecialtyDTO.builder().id(1).name("Cardiologia").build();
        var specialty = Specialty.builder().id(1).name("Cardiologia").build();

        when(specialtyService.fromDTO(specialtyDTO)).thenReturn(specialty);
        when(specialtyService.insert(any())).thenReturn(any());

        var response = specialtyController.insert(specialtyDTO);
        var uriGenerated = requireNonNull(response.getHeaders().get("Location")).get(0);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().containsKey("Location")).isTrue();
        assertThat(uriGenerated).isEqualTo(URI_EXPECTED);
    }
}
