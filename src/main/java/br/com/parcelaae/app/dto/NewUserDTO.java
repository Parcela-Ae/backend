package br.com.parcelaae.app.dto;

import br.com.parcelaae.app.domain.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String cpfOuCnpj;

    private List<Specialty> specialties;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String password;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String publicArea;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String number;

    private String complement;

    private String neighborhood;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String zipCode;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String phone1;

    private String phone2;

    private String phone3;

    private Integer cityId;
}
