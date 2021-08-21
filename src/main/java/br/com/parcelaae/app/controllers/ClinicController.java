package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/clinics")
public class ClinicController {

    @Autowired
    private ClinicService service;

    @GetMapping
    public ResponseEntity<List<Clinic>> listAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<Clinic>> find(@RequestBody ClinicFilter filter) {
        List<Clinic> clinics = service.find(filter);
        return ResponseEntity.ok().body(clinics);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NewUserDTO newUserDTO) {
        Clinic clinic = service.fromDTO(newUserDTO);
        service.insert(clinic);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clinic.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
