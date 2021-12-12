package br.com.parcelaae.app.domain.scheduling.service;

import br.com.parcelaae.app.domain.balancemovement.service.BalanceMovementService;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.model.ClinicApiResponse;
import br.com.parcelaae.app.domain.customer.model.Customer;
import br.com.parcelaae.app.domain.scheduling.model.Scheduling;
import br.com.parcelaae.app.domain.scheduling.model.SchedulingApiRequest;
import br.com.parcelaae.app.domain.scheduling.model.SchedulingApiResponse;
import br.com.parcelaae.app.domain.scheduling.repository.SchedulingRepository;
import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;

    private final BalanceMovementService balanceMovementService;

    public Scheduling save(SchedulingApiRequest schedulingApiRequest) {
        var payment = balanceMovementService.fromScheduleApiRequest(schedulingApiRequest);
        balanceMovementService.save(payment);
        var scheduling = toEntity(schedulingApiRequest);
        return schedulingRepository.save(scheduling);
    }

    public List<Scheduling> listAllByCustomerId(Integer customerId) {
        return schedulingRepository.findAllByCustomerId(customerId);
    }

    public List<Scheduling> listAllByClinicId(Integer clinicId) {
        return schedulingRepository.findAllByClinicId(clinicId);
    }

    public Scheduling toEntity(SchedulingApiRequest schedulingApiRequest) {
        var scheduling = new Scheduling();
        scheduling.setCustomer(Customer.builder().id(schedulingApiRequest.getCustomerId()).build());
        scheduling.setClinic(Clinic.builder().id(schedulingApiRequest.getClinicId()).build());
        scheduling.setSpecialty(Specialty.builder().id(schedulingApiRequest.getSpecialtyId()).build());
        scheduling.setScheduledTo(schedulingApiRequest.getScheduledTo());
        scheduling.setAppointmentValue(BigDecimal.valueOf(schedulingApiRequest.getValue()));
        scheduling.setAppointmentTime(schedulingApiRequest.getScheduledTo().getHour() + ":" + schedulingApiRequest.getScheduledTo().getMinute());
        return scheduling;
    }

    public SchedulingApiResponse toApiResponse(Scheduling scheduling) {
        return SchedulingApiResponse.builder()
                .clinic(ClinicApiResponse.builder()
                        .id(scheduling.getClinic().getId())
                        .name(scheduling.getClinic().getName())
                        .build())
                .specialty(SpecialtyApiModel.builder()
                        .id(scheduling.getSpecialty().getId())
                        .name(scheduling.getSpecialty().getName())
                        .build())
                .scheduledTo(scheduling.getScheduledTo())
                .appointmentValue(scheduling.getAppointmentValue())
                .appointmentTime(scheduling.getAppointmentTime())
                .build();
    }
}
