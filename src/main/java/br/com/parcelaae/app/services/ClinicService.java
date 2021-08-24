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
    private BCryptPasswordEncoder passwordEncoder;

    public User insert(User user) {
        user.setId(null);
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
        var clinica = new Clinic();
        clinica.setName(newUserDTO.getName());
        clinica.setEmail(newUserDTO.getEmail());
        clinica.setCnpj(newUserDTO.getCpfOuCnpj());
        clinica.setPassword(passwordEncoder.encode(newUserDTO.getPassword()));

        var cid = new City(newUserDTO.getCityId(), null, null);
        var end = Address.builder()
                .publicArea(newUserDTO.getPublicArea())
                .number(newUserDTO.getNumber())
                .complement(newUserDTO.getComplement())
                .neighborhood(newUserDTO.getNeighborhood())
                .zipCode(newUserDTO.getZipCode())
                .user(clinica)
                .city(cid)
                .build();
        var especialidades = newUserDTO.getSpecialties();

        clinica.getAddresses().add(end);
        clinica.getPhones().add(newUserDTO.getPhone1());
        clinica.getSpecialties().addAll(especialidades);
        if (newUserDTO.getPhone2()!=null) {
            clinica.getPhones().add(newUserDTO.getPhone2());
        }
        if (newUserDTO.getPhone3()!=null) {
            clinica.getPhones().add(newUserDTO.getPhone3());
        }
        return clinica;
    }
}
