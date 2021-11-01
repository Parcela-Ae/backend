package br.com.parcelaae.app.domain.customer.model;

import br.com.parcelaae.app.domain.address.model.Address;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionApiResponse;
import br.com.parcelaae.app.domain.feedback.model.FeedbackApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
public class CustomerApiResponse {

    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private Credit credit;
    private TransactionApiResponse lastTransaction;
    private List<Address> addresses;
    private Set<String> phones;
    private List<FeedbackApiResponse> feedbacks;
}
