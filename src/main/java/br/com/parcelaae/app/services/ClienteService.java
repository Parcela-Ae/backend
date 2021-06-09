package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.*;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.ClienteRepository;
import br.com.parcelaae.app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoService enderecoService;

    public List<Cliente> listAll() {
        return  clienteRepository.findAll();
    }

    public Usuario insert(Usuario usuario) {
        usuario.setId(null);
        usuario = usuarioRepository.save(usuario);
        enderecoService.saveAll(usuario.getEnderecos());
        return usuario;
    }

    public Cliente fromDTO(NewUserDTO newUserDTO) {
        var cliente = new Cliente();
        cliente.setNome(newUserDTO.getNome());
        cliente.setEmail(newUserDTO.getEmail());
        cliente.setCpf(newUserDTO.getCpfOuCnpj());
        cliente.setSenha(newUserDTO.getSenha());

        var cid = new Cidade(newUserDTO.getCidadeId(), null, null);
        var end = new Endereco(null, newUserDTO.getLogradouro(), newUserDTO.getNumero(), newUserDTO.getComplemento(), newUserDTO.getBairro(), newUserDTO.getCep(), cliente, cid);

        cliente.getEnderecos().add(end);
        cliente.getTelefones().add(newUserDTO.getTelefone1());
        if (newUserDTO.getTelefone2()!=null) {
            cliente.getTelefones().add(newUserDTO.getTelefone2());
        }
        if (newUserDTO.getTelefone3()!=null) {
            cliente.getTelefones().add(newUserDTO.getTelefone3());
        }
        return cliente;
    }
}
