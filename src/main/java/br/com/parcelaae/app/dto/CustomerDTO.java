package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Address;
import br.com.parcelaae.app.domain.Credit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
public class CustomerDTO {

    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private Credit credit;
    private TransactionDTO lastTransaction;
    private List<Address> addresses;
    private Set<String> phones;
    private List<FeedbackDTO> feedbacks;
}
