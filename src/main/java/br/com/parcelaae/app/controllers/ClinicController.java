package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.clinic.model.ClinicRestFilter;
import br.com.parcelaae.app.domain.clinic.model.Clinic;
import br.com.parcelaae.app.domain.clinic.model.ClinicApiResponse;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.clinic.service.ClinicService;
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
    public ResponseEntity<ClinicApiResponse> findById(@PathVariable("clinicId") Integer clinicId) {
        var clinic = clinicService.findById(clinicId);
        var clinicDTO = clinicService.fromEntity(clinic);
        return ResponseEntity.ok(clinicDTO);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<List<ClinicApiResponse>> find(@RequestBody ClinicRestFilter filter) {
        var clinics = clinicService.find(filter);
        var clinicsDTO = clinics.stream().map(clinicService::fromEntity).collect(Collectors.toList());
        return ResponseEntity.ok().body(clinicsDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody UserApiRequest userApiRequest) {
        Clinic clinic = clinicService.fromDTO(userApiRequest);
        clinicService.insert(clinic);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clinic.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
