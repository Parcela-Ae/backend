package br.com.parcelaae.app.services.validation;

import br.com.parcelaae.app.controllers.exceptions.FieldMessage;
import br.com.parcelaae.app.domain.User;
import br.com.parcelaae.app.dto.NewUserDTO;
import br.com.parcelaae.app.repositories.UserRepository;
import br.com.parcelaae.app.services.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsert, NewUserDTO> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public boolean isValid(NewUserDTO value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        User aux = userRepository.findByEmail(value.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "Email already exist"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
