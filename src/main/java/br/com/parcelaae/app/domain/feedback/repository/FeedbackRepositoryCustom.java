package br.com.parcelaae.app.domain.feedback.repository;

import br.com.parcelaae.app.domain.feedback.model.Feedback;
import br.com.parcelaae.app.domain.feedback.model.FeedbackRestFilter;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface FeedbackRepositoryCustom {

    List<Feedback> find(@NotNull FeedbackRestFilter filter);
}
