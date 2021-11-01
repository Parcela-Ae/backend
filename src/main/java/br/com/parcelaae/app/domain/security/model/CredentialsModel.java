package br.com.parcelaae.app.domain.security.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CredentialsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
}
