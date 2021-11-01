package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.customer.model.Customer;
import br.com.parcelaae.app.domain.customer.model.CustomerApiResponse;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static br.com.parcelaae.app.domain.user.service.UserService.validateIfUserHasAuthoritation;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerApiResponse> findById(@PathVariable("customerId") Integer customerId) {
        validateIfUserHasAuthoritation(customerId);
        var customer = service.findById(customerId);
        var customerDTO = service.toDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok().body(service.listAll());
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody UserApiRequest userApiRequest) {
        Customer customer = service.fromDTO(userApiRequest);
        service.insert(customer);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
