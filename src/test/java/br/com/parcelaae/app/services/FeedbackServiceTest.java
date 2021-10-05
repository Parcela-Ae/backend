package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.queryfilter.FeedbackFilter;
import br.com.parcelaae.app.domain.Feedback;
import br.com.parcelaae.app.repositories.FeedbackRepository;
import br.com.parcelaae.app.repositories.custom.FeedbackCustomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    FeedbackCustomRepository customRepository;

    private final List<Feedback> feedbacksExpected = new ArrayList<>();

    @BeforeEach
    void setUp() {
        feedbacksExpected.add(Feedback.builder().build());
    }

    @Test
    void shouldFindFeedbacksByCustumer() {
        var customerId = 1;
        when(feedbackRepository.findByCustomerId(customerId)).thenReturn(feedbacksExpected);

        var feedbacksActual = feedbackService.findByCustumer(customerId);

        assertThat(feedbacksActual).isNotEmpty();
    }

    @Test
    void shouldFindAllFeedbacks() {
        when(feedbackRepository.findAll()).thenReturn(feedbacksExpected);

        var feedbacksActual = feedbackService.findAll();

        assertThat(feedbacksActual).isNotEmpty();
    }

    @Test
    void shouldSaveAFeedback() {
        var newFeedback = Feedback.builder().build();
        var feedbackSaved = Feedback.builder().build();

        when(feedbackRepository.save(newFeedback)).thenReturn(feedbackSaved);

        feedbackService.save(newFeedback);

        verify(feedbackRepository).save(newFeedback);
    }

    @Test
    void shouldSearchForFeedbacksWithFilter() {
        var filter = FeedbackFilter.builder().build();

        when(customRepository.find(filter)).thenReturn(feedbacksExpected);

        var feedbacksActual = feedbackService.search(filter);

        assertThat(feedbacksActual).isNotEmpty();
    }
}
