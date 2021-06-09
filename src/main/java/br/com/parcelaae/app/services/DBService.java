package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.*;
import br.com.parcelaae.app.repositories.CidadeRepository;
import br.com.parcelaae.app.repositories.ClinicaRepository;
import br.com.parcelaae.app.repositories.EspecialidadeRepository;
import br.com.parcelaae.app.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DBService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    public void instantiateTestDatabase() {
        //String[] estados = {"Acre","Alagoas","Amapá","Amazonas","Bahia","Ceará","Distrito Federal","Espírito Santo","Goiás","Maranhão","Mato Grosso","Mato Grosso do Sul","Minas Gerais","Pará","Paraíba","Paraná","Pernambuco","Piauí","Rio de Janeiro","Rio Grande do Norte","Rio Grande do Sul","Rondônia","Roraima","Santa Catarina","São Paulo","Sergipe","Tocantins"};


        var estado1 = new Estado("Pernambuco");
        var estado2 = new Estado("São Paulo");

        var cidade1 = new Cidade(null, "Recife", estado1);
        var cidade2 = new Cidade(null, "Olinda", estado1);
        var cidade3 = new Cidade(null, "São Paulo", estado2);

        estado1.getCidades().addAll(List.of(cidade1, cidade2));
        estado2.getCidades().addAll(List.of(cidade3));

        estadoRepository.saveAll(List.of(estado1, estado2));
        cidadeRepository.saveAll(List.of(cidade1, cidade2, cidade3));

        var especialidade1 = new Especialidade("Clinica Geral");
        var especialidade2 = new Especialidade("Dermatologia");
        var especialidade3 = new Especialidade("Odontologia");

        especialidadeRepository.saveAll(List.of(especialidade1, especialidade2, especialidade3));

        var clinica1 = new Clinica("Ok Doutor", "ok@doutor.com", "123", "12345678910");
        clinica1.getTelefones().addAll(List.of("81994020345", "34353637"));
        clinica1.getEspecialidades().addAll(List.of(especialidade2, especialidade3));


        var clinica2 = new Clinica("Sirio Libanes", "sirio@libanes.com", "123", "10987654321");
        clinica2.getTelefones().addAll(List.of("11994020345", "32353937"));
        clinica2.getEspecialidades().addAll(List.of(especialidade1, especialidade2, especialidade3));

        var e1 = new Endereco(null, "Av. Agamenon Magalhães", "300", "Apto 303", "Espinheiro", "38220834", clinica1, cidade1);
        var e2 = new Endereco(null, "Presidente Kennedy", "105", null, "Centro", "38777012", clinica1, cidade2);
        var e3 = new Endereco(null, "Avenida Paulista", "2106", null, "Centro", "38777012", clinica2, cidade3);

        clinica1.getEnderecos().addAll(List.of(e1, e2));
        clinica2.getEnderecos().addAll(List.of(e3));

        clinicaRepository.saveAll(List.of(clinica1, clinica2));
    }
}
