package br.com.parcelaae.app.domain.feedback.service;

import br.com.parcelaae.app.domain.feedback.model.FeedbackRestFilter;
import br.com.parcelaae.app.domain.feedback.model.Feedback;
import br.com.parcelaae.app.domain.feedback.repository.FeedbackRepository;
import br.com.parcelaae.app.domain.feedback.repository.FeedbackCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final FeedbackCustomRepository customRepository;

    public List<Feedback> findByCustumer(Integer customerId) {
        return feedbackRepository.findByCustomerId(customerId);
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public List<Feedback> search(FeedbackRestFilter filter) {
        return customRepository.find(filter);
    }
}
