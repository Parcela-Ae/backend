package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.core.exception.AuthorizationException;
import br.com.parcelaae.app.domain.scheduling.model.Scheduling;
import br.com.parcelaae.app.domain.scheduling.model.SchedulingApiRequest;
import br.com.parcelaae.app.domain.scheduling.service.SchedulingService;
import br.com.parcelaae.app.domain.security.model.UserSS;
import br.com.parcelaae.app.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static br.com.parcelaae.app.core.constants.AppConstants.CLINIC;
import static br.com.parcelaae.app.core.constants.AppConstants.CUSTOMER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class SchedulingControllerTest {

    public static final String AUTHORIZATION_EXCEPTION_MESSAGE = "Não foi possível recuperar as informações do usuário no momento";
    public static final int CLINIC_ID = 1;
    public static final int CUSTOMER_ID = 1;

    private SchedulingController schedulingController;

    @Mock
    private SchedulingService schedulingService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        schedulingController = new SchedulingController(schedulingService, userService);
    }

    @Test
    void shouldInsertNewScheduling() {
        var schedulingApiRequest = SchedulingApiRequest.builder().build();

        var response = schedulingController.insert(schedulingApiRequest);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        verify(schedulingService, times(CLINIC_ID)).save(any());
    }

    @Test
    void shouldListAllSchedulesByClinic() {
        var authenticatedUser = UserSS.builder().id(CLINIC_ID).build();
        var schedules = Collections.singletonList(Scheduling.builder().build());

        try(MockedStatic<UserService> userServiceMockedStatic = Mockito.mockStatic(UserService.class)) {
            userServiceMockedStatic.when(UserService::getAuthenticatedUser).thenReturn(authenticatedUser);

            when(userService.isValidUser(authenticatedUser, CLINIC_ID, CLINIC)).thenReturn(true);
            when(schedulingService.listAllByClinicId(authenticatedUser.getId())).thenReturn(schedules);

            var response = schedulingController.listAllSchedulesByClinic(CLINIC_ID);

            assertThat(response.getStatusCode()).isEqualTo(OK);
            verify(schedulingService, atLeastOnce()).toApiResponse(any(Scheduling.class));
        }
    }

    @Test
    void shouldThrowAuthorizationExceptionWhenListAllSchedulesByClinic() {
        var authorizationException = catchThrowableOfType(
                () -> schedulingController.listAllSchedulesByClinic(CLINIC_ID),
                AuthorizationException.class
        );

        assertThat(authorizationException)
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining(AUTHORIZATION_EXCEPTION_MESSAGE);

        verify(schedulingService, never()).toApiResponse(any(Scheduling.class));
    }

    @Test
    void shouldListAllSchedulesByCustomer() {
        var authenticatedUser = UserSS.builder().id(CUSTOMER_ID).build();
        var schedules = Collections.singletonList(Scheduling.builder().build());

        try(MockedStatic<UserService> userServiceMockedStatic = Mockito.mockStatic(UserService.class)) {
            userServiceMockedStatic.when(UserService::getAuthenticatedUser).thenReturn(authenticatedUser);

            when(userService.isValidUser(authenticatedUser, CUSTOMER_ID, CUSTOMER)).thenReturn(true);
            when(schedulingService.listAllByCustomerId(authenticatedUser.getId())).thenReturn(schedules);

            var response = schedulingController.listAllSchedulesByCustomer(CUSTOMER_ID);

            assertThat(response.getStatusCode()).isEqualTo(OK);
            verify(schedulingService, atLeastOnce()).toApiResponse(any(Scheduling.class));
        }
    }

    @Test
    void shouldThrowAuthorizationExceptionWhenListAllSchedulesByCustomer() {
        var authorizationException = catchThrowableOfType(
                () -> schedulingController.listAllSchedulesByCustomer(CUSTOMER_ID),
                AuthorizationException.class
        );

        assertThat(authorizationException)
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining(AUTHORIZATION_EXCEPTION_MESSAGE);

        verify(schedulingService, never()).toApiResponse(any(Scheduling.class));
    }
}