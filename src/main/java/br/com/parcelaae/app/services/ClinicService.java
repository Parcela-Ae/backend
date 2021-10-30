package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.dto.ClinicDTO;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.ClinicRepository;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.repositories.custom.ClinicCustomRepository;
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

    public List<Clinic> find(ClinicFilter filter) {
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

    public Clinic fromDTO(NewUserDTO newUserDTO) {
        var clinic = new Clinic();
        clinic.setName(newUserDTO.getName());
        clinic.setEmail(newUserDTO.getEmail());
        clinic.setCnpj(newUserDTO.getCpfOuCnpj());
        clinic.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        var end = Address.builder()
                .publicArea(newUserDTO.getPublicArea())
                .number(newUserDTO.getNumber())
                .complement(newUserDTO.getComplement())
                .neighborhood(newUserDTO.getNeighborhood())
                .zipCode(newUserDTO.getZipCode())
                .user(clinic)
                .city(newUserDTO.getCity())
                .state(newUserDTO.getState())
                .build();
        var especialidades = newUserDTO.getSpecialties();

        clinic.getAddresses().add(end);
        clinic.getPhones().add(newUserDTO.getPhone1());
        clinic.getSpecialties().addAll(especialidades);
        if (newUserDTO.getPhone2()!=null) {
            clinic.getPhones().add(newUserDTO.getPhone2());
        }
        if (newUserDTO.getPhone3()!=null) {
            clinic.getPhones().add(newUserDTO.getPhone3());
        }
        return clinic;
    }

    public ClinicDTO fromEntity(Clinic clinic) {
        var clinicDTO = new ClinicDTO();
        BeanUtils.copyProperties(clinic, clinicDTO);
        return clinicDTO;
    }
}
