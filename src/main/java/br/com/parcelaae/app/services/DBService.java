package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.*;
import br.com.parcelaae.app.domain.enums.Profile;
import br.com.parcelaae.app.repositories.CityRepository;
import br.com.parcelaae.app.repositories.ClinicRepository;
import br.com.parcelaae.app.repositories.SpecialtyRepository;
import br.com.parcelaae.app.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DBService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void instantiateTestDatabase() {
        //String[] estados = {"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};


        var estado1 = State.builder().name("Pernambuco").build();
        var estado2 = State.builder().name("São Paulo").build();

        var cidade1 = new City(null, "Recife", estado1);
        var cidade2 = new City(null, "Olinda", estado1);
        var cidade3 = new City(null, "São Paulo", estado2);

        estado1.getCities().addAll(List.of(cidade1, cidade2));
        estado2.getCities().addAll(List.of(cidade3));

        stateRepository.saveAll(List.of(estado1, estado2));
        cityRepository.saveAll(List.of(cidade1, cidade2, cidade3));

        var especialidade1 = Specialty.builder().name("Clinica Geral").build();
        var especialidade2 = Specialty.builder().name("Dermatologia").build();
        var especialidade3 = Specialty.builder().name("Odontologia").build();

        specialtyRepository.saveAll(List.of(especialidade1, especialidade2, especialidade3));

        var clinica1 = new Clinic("Ok Doutor", "ok@doutor.com", passwordEncoder.encode("123"), "12345678910");
        clinica1.getTelefones().addAll(List.of("81994020345", "34353637"));
        clinica1.getSpecialties().addAll(List.of(especialidade2, especialidade3));
        clinica1.addPerfil(Profile.ADMIN);


        var clinica2 = new Clinic("Sirio Libanes", "sirio@libanes.com", passwordEncoder.encode("123"), "10987654321");
        clinica2.getTelefones().addAll(List.of("11994020345", "32353937"));
        clinica2.getSpecialties().addAll(List.of(especialidade1, especialidade2, especialidade3));

        var e1 = Address.builder()
                .publicArea("Av. Agamenon Magalhães")
                .number("300")
                .complement("Apto 303")
                .neighborhood("Espinheiro")
                .zipCode("38220834")
                .user(clinica1)
                .city(cidade1)
                .build();
        var e2 = Address.builder()
                .publicArea("Presidente Kennedy")
                .number("105")
                .neighborhood("Centro")
                .zipCode("38777012")
                .user(clinica1)
                .city(cidade2)
                .build();
        var e3 = Address.builder()
                .publicArea("Avenida Paulista")
                .number("2106")
                .neighborhood("Centro")
                .zipCode("39867024")
                .user(clinica2)
                .city(cidade3)
                .build();

        clinica1.getAddresses().addAll(List.of(e1, e2));
        clinica2.getAddresses().addAll(List.of(e3));

        clinicRepository.saveAll(List.of(clinica1, clinica2));
    }
}
