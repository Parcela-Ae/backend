package br.com.parcelaae.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld")
public class TestController {

    @GetMapping
    public ResponseEntity<String> helloWord() {
       return ResponseEntity.ok().body("Hello World!");
    }
}
