package br.com.parcelaae.app.domain.clinic.service;

import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.address.service.AddressService;
import br.com.parcelaae.app.domain.clinic.model.ClinicApiResponse;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.clinic.repository.ClinicRepository;
import br.com.parcelaae.app.domain.user.repository.UserRepository;
import br.com.parcelaae.app.domain.clinic.repository.ClinicCustomRepository;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ClinicService {

    private final UserRepository userRepository;

    private final ClinicRepository clinicRepository;

    private final ClinicCustomRepository clinicCustomRepository;

    private final AddressService addressService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CreditService creditService;

    public User insert(User user) {
        user = userRepository.save(user);
        addressService.saveAll(user.getAddresses());
        creditService.save(new Credit(null, user, 0.0));
        return user;
    }

    public Clinic findById(Integer clinicId) {
        return clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ObjectNotFoundException(Clinic.class, "Não foi encontrada uma clínica para o ID informado"));
    }

    public List<Clinic> find(ClinicRestFilter filter) {
        return clinicCustomRepository.find(filter);
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
        var especialidades = userApiRequest.getSpecialties();

        clinic.getAddresses().add(end);
        clinic.getPhones().add(userApiRequest.getPhone1());
        clinic.getSpecialties().addAll(especialidades);
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
        return clinicDTO;
    }
}
