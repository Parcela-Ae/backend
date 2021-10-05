package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.Clinic;
import br.com.parcelaae.app.domain.Customer;
import br.com.parcelaae.app.domain.Feedback;
import br.com.parcelaae.app.dto.NewFeedbackDTO;
import br.com.parcelaae.app.services.FeedbackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private FeedbackService feedbackService;

    @Test
    void shouldFindByCostumerId() {
        var feedback = Feedback.builder()
                .id(1)
                .message("Excelente")
                .clinic(Clinic.builder().id(1).name("OK Doutor").build())
                .customer(Customer.builder().id(2).name("John Wick").build())
                .rating(5F)
                .build();
        var feedbacks = singletonList(feedback);

        when(feedbackService.search(any())).thenReturn(feedbacks);

        var response = feedbackController.findByCostumerId(any());
        var feedbacksResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(feedbacksResponse).isNotNull();
        assertThat(feedbacksResponse.get(0).getClinicId()).isEqualTo(feedback.getClinic().getId());
        assertThat(feedbacksResponse.get(0).getClinicName()).isEqualTo(feedback.getClinic().getName());
        assertThat(feedbacksResponse.get(0).getCustomerId()).isEqualTo(feedback.getCustomer().getId());
        assertThat(feedbacksResponse.get(0).getCustomerName()).isEqualTo(feedback.getCustomer().getName());
        assertThat(feedbacksResponse.get(0).getMessage()).isEqualTo(feedback.getMessage());
        assertThat(feedbacksResponse.get(0).getRating()).isEqualTo(feedback.getRating());
    }

    @Test
    void shouldInsertNewFeedback() {
        var newFeedbackDTO = NewFeedbackDTO.builder().build();

        doNothing().when(feedbackService).save(any());

        var response = feedbackController.insertNewFeedback(newFeedbackDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
    }
}
