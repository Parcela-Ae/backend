package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.core.exception.AuthorizationException;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.model.ClinicApiResponse;
import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.clinic.service.ClinicService;
import br.com.parcelaae.app.domain.security.model.UserSS;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static br.com.parcelaae.app.core.constants.AppConstants.CLINIC;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class ClinicControllerTest {

    public static final String CLINIC_NAME = "Ok Doutor";
    public static final String URI_EXPECTED = "http://localhost/clinics/1";
    public static final String AUTHORIZATION_EXCEPTION_MESSAGE = "Acesso negado";

    @InjectMocks
    private ClinicController clinicController;

    @Mock
    private ClinicService clinicService;

    @Mock
    private UserService userService;

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
    void shouldFindClinicById() {
        var clinicId = 1;
        var clinic = Clinic.builder().id(clinicId).build();
        var clinicApiResponseExpected = ClinicApiResponse.builder().id(clinicId).build();

        try(MockedStatic<UserService> userServiceMockedStatic = Mockito.mockStatic(UserService.class)) {
            when(clinicService.findById(clinicId)).thenReturn(clinic);
            when(clinicService.fromEntity(clinic)).thenReturn(clinicApiResponseExpected);

            var response = clinicController.findById(clinicId);

            assertThat(response.getStatusCode()).isEqualTo(OK);
            assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(clinicApiResponseExpected);
        }
    }

    @Test
    void shouldFindWithFilter() {
        var filter = ClinicRestFilter.builder().name(CLINIC_NAME).build();
        var clinic = Clinic.builder().name(CLINIC_NAME).build();
        var clinics = List.of(clinic);
        var clinicDTO = new ClinicApiResponse();
        var clinicsDTOExpected= List.of(clinicDTO);
        BeanUtils.copyProperties(clinic, clinicDTO);

        when(clinicService.find(filter)).thenReturn(clinics);
        when(clinicService.fromEntity(clinic)).thenReturn(clinicDTO);

        var response = clinicController.find(filter);
        var responseBody = response.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(responseBody).usingRecursiveFieldByFieldElementComparator().isEqualTo(clinicsDTOExpected);
    }

    @Test
    void shouldInsertANewClinic() {
        var newUserDTO = UserApiRequest.builder()
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

    @Test
    void shouldListAllSpecialtiesByClinicId() {
        var clinicId = 1;

        when(clinicService.listAllSpecialtiesByClinicId(clinicId)).thenReturn(anyList());

        var response = clinicController.listAllSpecialties(clinicId);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldEmptyBodyWhenListAllSpecialtiesByClinicId() {
        var clinicId = 1;

        when(clinicService.listAllSpecialtiesByClinicId(clinicId)).thenReturn(null);

        var response = clinicController.listAllSpecialties(clinicId);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldSaveSpecialties() {
        var clinicId = 1;
        var specialties = Collections.singletonList(SpecialtyApiModel.builder().build());
        var authenticatedUser = UserSS.builder().build();

        try(MockedStatic<UserService> userServiceMockedStatic = Mockito.mockStatic(UserService.class)) {
            userServiceMockedStatic.when(UserService::getAuthenticatedUser).thenReturn(authenticatedUser);

            when(userService.isValidUser(authenticatedUser, clinicId, CLINIC)).thenReturn(true);

            var response = clinicController.saveSpecialties(clinicId, specialties);

            assertThat(response.getStatusCode()).isEqualTo(OK);
            verify(clinicService, times(1)).saveSpecialties(clinicId, specialties);
        }
    }

    @Test
    void shouldThrowAuthorizationExceptionWhenUserUnauthorizedSaveSpecialties() {
        var clinicId = 1;
        var specialties = Collections.singletonList(SpecialtyApiModel.builder().build());

        when(userService.isValidUser(null, clinicId, CLINIC)).thenReturn(false);

        var authorizationException = catchThrowableOfType(
                () -> clinicController.saveSpecialties(clinicId, specialties),
                AuthorizationException.class);

        assertThat(authorizationException)
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining(AUTHORIZATION_EXCEPTION_MESSAGE);

        verify(clinicService, never()).saveSpecialties(any(), anyList());
    }
}
