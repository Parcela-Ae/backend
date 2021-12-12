package br.com.parcelaae.app.domain.feedback.service;

import br.com.parcelaae.app.domain.feedback.model.Feedback;
import br.com.parcelaae.app.domain.feedback.model.FeedbackRestFilter;
import br.com.parcelaae.app.domain.feedback.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

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
        return feedbackRepository.find(filter);
    }
}
