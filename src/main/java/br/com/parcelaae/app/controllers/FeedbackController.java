package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.FeedbackFilter;
import br.com.parcelaae.app.dto.FeedbackDTO;
import br.com.parcelaae.app.dto.NewFeedbackDTO;
import br.com.parcelaae.app.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/search")
    public ResponseEntity<List<FeedbackDTO>> findByCostumerId(@RequestBody FeedbackFilter feedbackFilter) {
        var feedbacks = feedbackService.search(feedbackFilter);
        var feedbacksDTO = feedbacks.stream().map(FeedbackDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(feedbacksDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insertNewFeedback(@Valid @RequestBody NewFeedbackDTO newFeedbackDTO) {
        var newFeedback = NewFeedbackDTO.toEntity(newFeedbackDTO);
        feedbackService.save(newFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
