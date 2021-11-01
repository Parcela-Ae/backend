package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.feedback.model.FeedbackRestFilter;
import br.com.parcelaae.app.domain.feedback.model.FeedbackApiResponse;
import br.com.parcelaae.app.domain.feedback.model.FeedbackApiRequest;
import br.com.parcelaae.app.domain.feedback.service.FeedbackService;
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
    public ResponseEntity<List<FeedbackApiResponse>> findByCostumerId(@RequestBody FeedbackRestFilter feedbackRestFilter) {
        var feedbacks = feedbackService.search(feedbackRestFilter);
        var feedbacksDTO = feedbacks.stream().map(FeedbackApiResponse::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(feedbacksDTO);
    }

    @PostMapping
    public ResponseEntity<Void> insertNewFeedback(@Valid @RequestBody FeedbackApiRequest feedbackApiRequest) {
        var newFeedback = FeedbackApiRequest.toEntity(feedbackApiRequest);
        feedbackService.save(newFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
