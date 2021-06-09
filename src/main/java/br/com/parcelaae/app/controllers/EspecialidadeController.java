package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Especialidade;
import br.com.parcelaae.app.dto.EspecialidadeDTO;
import br.com.parcelaae.app.services.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    @GetMapping
    public ResponseEntity<List<EspecialidadeDTO>> find() {
        List<EspecialidadeDTO> especialidadeDTOList = especialidadeService.listAll().stream().map(EspecialidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(especialidadeDTOList);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody EspecialidadeDTO especialidadeDTO) {
        Especialidade especialidade = especialidadeService.fromDTO(especialidadeDTO);
        especialidadeService.insert(especialidade);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(especialidade.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
