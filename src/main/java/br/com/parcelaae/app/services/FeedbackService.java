package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.Feedback;
import br.com.parcelaae.app.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    public List<Feedback> findByClient(Integer customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<Feedback> findAll() {
        return repository.findAll();
    }
}
