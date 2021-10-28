package br.com.parcelaae.app.services.validation;

import br.com.parcelaae.app.services.validation.utils.DocumentUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return DocumentUtil.isValidCpf(value) || DocumentUtil.isValidCnpj(value);
    }
}
