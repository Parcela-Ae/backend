package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.core.exception.AuthorizationException;
import br.com.parcelaae.app.domain.scheduling.model.Scheduling;
import br.com.parcelaae.app.domain.scheduling.model.SchedulingApiRequest;
import br.com.parcelaae.app.domain.scheduling.model.SchedulingApiResponse;
import br.com.parcelaae.app.domain.scheduling.service.SchedulingService;
import br.com.parcelaae.app.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static br.com.parcelaae.app.core.constants.AppConstants.CLINIC;
import static br.com.parcelaae.app.core.constants.AppConstants.CUSTOMER;

@AllArgsConstructor
@RestController
public class SchedulingController {

    private final SchedulingService schedulingService;
    private final UserService userService;

    @PostMapping(path = "schedules")
    public ResponseEntity<Void> insert(@RequestBody @Valid SchedulingApiRequest schedulingApiRequest) {
        schedulingService.save(schedulingApiRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "clinics/{clinicId}/schedules")
    public ResponseEntity<List<SchedulingApiResponse>> listAllSchedulesByClinic(@PathVariable("clinicId") Integer clinicId) {
        var authenticatedUser = UserService.getAuthenticatedUser();
        List<Scheduling> schedules;
        List<SchedulingApiResponse> schedulesResponse = new ArrayList<>();

        if (!userService.isValidUser(authenticatedUser, clinicId, CLINIC))
            throw new AuthorizationException("Não foi possível recuperar as informações do usuário no momento");

        schedules = schedulingService.listAllByClinicId(authenticatedUser.getId());

        schedules.forEach(scheduling -> schedulesResponse.add(schedulingService.toApiResponse(scheduling)));

        return ResponseEntity.ok(schedulesResponse);
    }

    @GetMapping(path = "customers/{customerId}/schedules")
    public ResponseEntity<List<SchedulingApiResponse>> listAllSchedulesByCustomer(@PathVariable("customerId") Integer customerId) {
        var authenticatedUser = UserService.getAuthenticatedUser();
        List<Scheduling> schedules;
        List<SchedulingApiResponse> schedulesResponse = new ArrayList<>();

        if (!userService.isValidUser(authenticatedUser, customerId, CUSTOMER))
            throw new AuthorizationException("Não foi possível recuperar as informações do usuário no momento");

        schedules =  schedulingService.listAllByCustomerId(customerId);

        schedules.forEach(scheduling -> schedulesResponse.add(schedulingService.toApiResponse(scheduling)));

        return ResponseEntity.ok(schedulesResponse);
    }
}
