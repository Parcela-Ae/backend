package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.queryfilter.FeedbackFilter;
import br.com.parcelaae.app.dto.FeedbackDTO;
import br.com.parcelaae.app.dto.NewFeedbackDTO;
import br.com.parcelaae.app.services.FeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

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
