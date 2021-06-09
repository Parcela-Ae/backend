package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Feedback;
import br.com.parcelaae.app.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<Feedback>> findByCostumerId(@PathVariable("customerId") Integer customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.findByClient(customerId));
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackService.findAll());
    }
}
