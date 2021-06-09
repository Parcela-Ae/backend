package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.*;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.ClinicaRepository;
import br.com.parcelaae.app.repositories.UsuarioRepository;
import br.com.parcelaae.app.repositories.impl.ClinicaCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ClinicaCustomRepository clinicaCustomRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario insert(Usuario usuario) {
        usuario.setId(null);
        usuario = usuarioRepository.save(usuario);
        enderecoService.saveAll(usuario.getEnderecos());
        return usuario;
    }

    public List<Clinica> find(ClinicFilter filter) {
        return clinicaCustomRepository.find(filter);
    }

    public List<Clinica> listAll() {
        return  clinicaRepository.findAll();
    }

    /**
     * // TODO: 06/06/2021 Criar método para atualizar somente campos específicos
     */
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Integer usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    public Clinica fromDTO(NewUserDTO newUserDTO) {
        var clinica = new Clinica();
        clinica.setNome(newUserDTO.getNome());
        clinica.setEmail(newUserDTO.getEmail());
        clinica.setCnpj(newUserDTO.getCpfOuCnpj());
        clinica.setSenha(passwordEncoder.encode(newUserDTO.getSenha()));

        var cid = new Cidade(newUserDTO.getCidadeId(), null, null);
        var end = new Endereco(null, newUserDTO.getLogradouro(), newUserDTO.getNumero(), newUserDTO.getComplemento(), newUserDTO.getBairro(), newUserDTO.getCep(), clinica, cid);
        var especialides = newUserDTO.getEspecialidades();

        clinica.getEnderecos().add(end);
        clinica.getTelefones().add(newUserDTO.getTelefone1());
        clinica.getEspecialidades().addAll(especialides);
        if (newUserDTO.getTelefone2()!=null) {
            clinica.getTelefones().add(newUserDTO.getTelefone2());
        }
        if (newUserDTO.getTelefone3()!=null) {
            clinica.getTelefones().add(newUserDTO.getTelefone3());
        }
        return clinica;
    }
}
