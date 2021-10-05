package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Specialty;
import br.com.parcelaae.app.dto.SpecialtyDTO;
import br.com.parcelaae.app.services.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> listAll() {
        List<SpecialtyDTO> specialtyDTOList = specialtyService.listAll().stream().map(SpecialtyDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(specialtyDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody SpecialtyDTO specialtyDTO) {
        Specialty specialty = specialtyService.fromDTO(specialtyDTO);
        specialtyService.insert(specialty);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(specialty.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
