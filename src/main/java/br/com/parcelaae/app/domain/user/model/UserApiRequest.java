package br.com.parcelaae.app.domain.user.model;

import br.com.parcelaae.app.domain.specialty.model.Specialty;
import br.com.parcelaae.app.core.validation.CpfCnpj;
import br.com.parcelaae.app.core.validation.UserInsert;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@UserInsert
public class UserApiRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String name;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @CpfCnpj
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

    private String city;

    private String state;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String phone1;

    private String phone2;

    private String phone3;

    private Integer cityId;
}
