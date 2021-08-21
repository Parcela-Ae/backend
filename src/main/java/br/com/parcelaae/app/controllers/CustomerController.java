package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Customer>> find() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NewUserDTO newUserDTO) {
        Customer customer = service.fromDTO(newUserDTO);
        service.insert(customer);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
