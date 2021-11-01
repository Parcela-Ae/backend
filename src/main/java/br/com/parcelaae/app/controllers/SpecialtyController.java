package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.domain.specialty.model.SpecialtyApiModel;
import br.com.parcelaae.app.domain.specialty.service.SpecialtyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<SpecialtyApiModel>> listAll() {
        List<SpecialtyApiModel> specialtyApiModelList = specialtyService.listAll().stream().map(SpecialtyApiModel::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(specialtyApiModelList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody SpecialtyApiModel specialtyApiModel) {
        Specialty specialty = specialtyService.fromDTO(specialtyApiModel);
        specialtyService.insert(specialty);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(specialty.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
