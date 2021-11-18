package br.com.parcelaae.app.core.validation;

import br.com.parcelaae.app.core.exception.FieldMessage;
import br.com.parcelaae.app.domain.user.model.User;
import br.com.parcelaae.app.domain.user.model.UserApiRequest;
import br.com.parcelaae.app.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserInsertValidator implements ConstraintValidator<UserInsert, UserApiRequest> {

    private final UserService userService;

    @Override
    public boolean isValid(UserApiRequest value, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        User aux = userService.findByEmail(value.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "Email já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
