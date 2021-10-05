package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping(value = "/search")
    public ResponseEntity<List<Clinic>> find(@RequestBody ClinicFilter filter) {
        List<Clinic> clinics = clinicService.find(filter);
        return ResponseEntity.ok().body(clinics);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody NewUserDTO newUserDTO) {
        Clinic clinic = clinicService.fromDTO(newUserDTO);
        clinicService.insert(clinic);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clinic.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
