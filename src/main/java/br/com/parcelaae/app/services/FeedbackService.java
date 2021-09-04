package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.queryfilter.FeedbackFilter;
import br.com.parcelaae.app.domain.Feedback;
import br.com.parcelaae.app.repositories.FeedbackRepository;
import br.com.parcelaae.app.repositories.custom.FeedbackCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    @Autowired
    FeedbackCustomRepository customRepository;

    public List<Feedback> findByClient(Integer customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<Feedback> findAll() {
        return repository.findAll();
    }

    public void save(Feedback feedback) {
        repository.save(feedback);
    }

    public List<Feedback> search(FeedbackFilter filter) {
        return customRepository.find(filter);
    }
}
