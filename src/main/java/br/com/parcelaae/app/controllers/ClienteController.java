package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Cliente;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> find() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NewUserDTO newUserDTO) {
        Cliente cliente = service.fromDTO(newUserDTO);
        service.insert(cliente);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
