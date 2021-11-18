package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return ResponseEntity.ok("Email confirmado com sucesso!");
    }
}
