package br.com.parcelaae.app.domain.clinic.service;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.address.service.AddressService;
import br.com.parcelaae.app.domain.balancemovement.service.BalanceMovementService;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.model.ClinicApiResponse;
import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.clinic.model.ClinicSpecialty;
import br.com.parcelaae.app.domain.clinic.repository.ClinicRepository;
import br.com.parcelaae.app.domain.clinic.repository.ClinicSpecialtyRepository;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Transactional
@AllArgsConstructor
@Service
public class ClinicService {

    private final UserRepository userRepository;

    private final ClinicRepository clinicRepository;

    private final ClinicSpecialtyRepository clinicSpecialtyRepository;

    private final AddressService addressService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CreditService creditService;

    private final BalanceMovementService balanceMovementService;

    public User insert(User user) {
        var specialties = user.getSpecialties();
        user.setSpecialties(null);

        user = userRepository.save(user);

        addressService.saveAll(user.getAddresses());
        creditService.save(new Credit(null, user, 0.0));
        clinicSpecialtyRepository.saveAll(specialties);
        return user;
    }

    public Clinic findById(Integer clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ObjectNotFoundException(Clinic.class, "Não foi encontrada uma clínica para o ID informado"));
    }

    public List<Clinic> find(ClinicRestFilter filter) {
        return clinicRepository.find(filter);
    }

    public List<Clinic> listAll() {
        return  clinicRepository.findAll();
    }

    /**
     * // TODO: 06/06/2021 Criar método para atualizar somente campos específicos
     */
    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Integer usuarioId) {
        userRepository.deleteById(usuarioId);
    }

    public void addAppointmentValue(Integer clinicId, Integer specialtyId, BigDecimal appointmentValue) {
        clinicRepository.addAppointmentValue(clinicId, specialtyId, appointmentValue);
    }

    public BigDecimal getAppointmentValue(Integer clinicId, Integer specialtyId) {
        return clinicRepository.getAppointmentValue(clinicId, specialtyId);
    }

    public Clinic fromDTO(UserApiRequest userApiRequest) {
        var clinic = new Clinic();
        clinic.setName(userApiRequest.getName());
        clinic.setEmail(userApiRequest.getEmail());
        clinic.setCnpj(userApiRequest.getCpfOuCnpj());
        clinic.setPassword(passwordEncoder.encode(userApiRequest.getPassword()));

        var end = Address.builder()
                .publicArea(userApiRequest.getPublicArea())
                .number(userApiRequest.getNumber())
                .complement(userApiRequest.getComplement())
                .neighborhood(userApiRequest.getNeighborhood())
                .zipCode(userApiRequest.getZipCode())
                .user(clinic)
                .city(userApiRequest.getCity())
                .state(userApiRequest.getState())
                .build();

        if (nonNull(userApiRequest.getSpecialties()) && !userApiRequest.getSpecialties().isEmpty())
            userApiRequest.getSpecialties().forEach(clinic::addSpecialty);

        clinic.getAddresses().add(end);
        clinic.getPhones().add(userApiRequest.getPhone1());

        if (userApiRequest.getPhone2()!=null) {
            clinic.getPhones().add(userApiRequest.getPhone2());
        }
        if (userApiRequest.getPhone3()!=null) {
            clinic.getPhones().add(userApiRequest.getPhone3());
        }
        return clinic;
    }

    public ClinicApiResponse fromEntity(Clinic clinic) {
        var clinicDTO = new ClinicApiResponse();
        BeanUtils.copyProperties(clinic, clinicDTO);
        clinicDTO.getSpecialties().addAll(clinic.getSpecialties().stream()
                .map(SpecialtyApiModel::new).collect(Collectors.toList()));
        return clinicDTO;
    }

    public List<ClinicSpecialty> listAllSpecialtiesByClinicId(Integer clinicId) {
        return clinicSpecialtyRepository.listAllSpecialtiesByClinicId(clinicId);
    }

    public void saveSpecialties(Integer clinicId, List<SpecialtyApiModel> specialties) {
        var clinicSpecialties = listAllSpecialtiesByClinicId(clinicId);
        var clinic = Clinic.builder().id(clinicId).build();

        if (nonNull(clinicSpecialties) && !clinicSpecialties.isEmpty()) {
            var clinicSpecialtyIds = clinicSpecialties.stream()
                    .map(ClinicSpecialty::getClinicSpecialtyId).collect(Collectors.toList());
            clinicSpecialtyRepository.deleteAllById(clinicSpecialtyIds);
        }

        clinicSpecialties = new ArrayList<>();

        for (SpecialtyApiModel specialty : specialties) {
            clinicSpecialties.add(new ClinicSpecialty(clinic, new Specialty(specialty), specialty.getAppointmentValue()));
        }

        clinicSpecialtyRepository.saveAll(clinicSpecialties);
    }
}
