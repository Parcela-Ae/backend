package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.*;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.ClinicRepository;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.repositories.custom.ClinicCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ClinicCustomRepository clinicCustomRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User insert(User user) {
        user = userRepository.save(user);
        addressService.saveAll(user.getAddresses());
        return user;
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

        validCity(newUserDTO);
        var cid = new City(newUserDTO.getCityId(), null, null);
        var end = Address.builder()
                .publicArea(newUserDTO.getPublicArea())
                .number(newUserDTO.getNumber())
                .complement(newUserDTO.getComplement())
                .neighborhood(newUserDTO.getNeighborhood())
                .zipCode(newUserDTO.getZipCode())
                .user(clinic)
                .city(cid)
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

    private void validCity(NewUserDTO newUserDTO) {
        if (!cityService.isAValidCity(newUserDTO.getCityId(), newUserDTO.getZipCode())) {
            throw new IllegalArgumentException("Invalid city id");
        }
    }
}
