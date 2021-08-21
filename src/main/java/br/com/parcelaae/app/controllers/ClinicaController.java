package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinica;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/clinicas")
public class ClinicaController {

    @Autowired
    private ClinicaService service;

    @GetMapping
    public ResponseEntity<List<Clinica>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<Clinica>> find(@RequestBody ClinicFilter filter) {
        List<Clinica> clinicas = service.find(filter);
        return ResponseEntity.ok().body(clinicas);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NewUserDTO newUserDTO) {
        Clinica clinica = service.fromDTO(newUserDTO);
        service.insert(clinica);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clinica.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
