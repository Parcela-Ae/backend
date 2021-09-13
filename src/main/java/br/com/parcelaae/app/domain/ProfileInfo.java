package br.com.parcelaae.app.domain;

import br.com.parcelaae.app.domain.enums.CustomerType;
import lombok.Data;

@Data
public class ProfileInfo {

    private Long id;
    private String email;
    private String name;
    private CustomerType costumer;
}
