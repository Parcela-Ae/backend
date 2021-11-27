package br.com.parcelaae.app.domain.feedback;

import br.com.parcelaae.app.domain.feedback.model.FeedbackRestFilter;
import br.com.parcelaae.app.domain.feedback.model.Feedback;
import br.com.parcelaae.app.domain.feedback.repository.FeedbackRepository;
import br.com.parcelaae.app.domain.feedback.repository.FeedbackRepositoryCustom;
import br.com.parcelaae.app.domain.feedback.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        var filter = FeedbackRestFilter.builder().build();

        when(feedbackRepository.find(filter)).thenReturn(feedbacksExpected);

        var feedbacksActual = feedbackService.search(filter);

        assertThat(feedbacksActual).isNotEmpty();
    }
}
