package br.com.parcelaae.app.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.stream.Stream;

public class AccountNumberGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        var query = String.format("select %s from %s",
                session.getEntityPersister(object.getClass().getName(), object).getIdentifierPropertyName(),
                object.getClass().getSimpleName());

        var accountNumbersExisting =  session.createQuery(query).stream();

        return generateNewAccountNumber(accountNumbersExisting);
    }

    private Long generateNewAccountNumber(Stream<?> accountNumbersExisting) {
        var newAccountNumber = generateAccountNumber();
        var accountNumberExisting = accountNumbersExisting.filter(accountNumber -> accountNumber.equals(newAccountNumber)).findFirst();

        return accountNumberExisting.isPresent() ? generateNewAccountNumber(accountNumbersExisting) : newAccountNumber;
    }

    private Long generateAccountNumber() {
        var secureRandom = new SecureRandom();
        return secureRandom.longs(1, 10000, 99999).findFirst().orElse(0L);
    }
}
