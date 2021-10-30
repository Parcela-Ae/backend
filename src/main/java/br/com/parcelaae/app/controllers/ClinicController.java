package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.ClinicFilter;
import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.dto.ClinicDTO;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.ClinicService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping("/{clinicId}")
    public ResponseEntity<ClinicDTO> findById(@PathVariable("clinicId") Integer clinicId) {
        var clinic = clinicService.findById(clinicId);
        var clinicDTO = clinicService.fromEntity(clinic);
        return ResponseEntity.ok(clinicDTO);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<ClinicDTO>> find(@RequestBody ClinicFilter filter) {
        var clinics = clinicService.find(filter);
        var clinicsDTO = clinics.stream().map(clinicService::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok().body(clinicsDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody NewUserDTO newUserDTO) {
        Clinic clinic = clinicService.fromDTO(newUserDTO);
        clinicService.insert(clinic);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clinic.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
