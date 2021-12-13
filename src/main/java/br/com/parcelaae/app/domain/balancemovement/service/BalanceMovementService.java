package br.com.parcelaae.app.domain.balancemovement.service;

import br.com.parcelaae.app.core.exception.BalanceInsufficientException;
import br.com.parcelaae.app.domain.balancemovement.model.*;
import br.com.parcelaae.app.domain.balancemovement.repository.BalanceMovementRepository;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import br.com.parcelaae.app.domain.scheduling.model.SchedulingApiRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
@Slf4j
@Service
public class BalanceMovementService {

    private final BalanceMovementRepository balanceMovementRepository;

    private final CreditService creditService;

    public void save(TransactionApiRequest transactionApiRequest) {
        var balanceMovement = fromDTO(transactionApiRequest);

        if (TransactionType.RECHARGE.equals(balanceMovement.getType()))
            rechargeCredit(balanceMovement);
        else {
            payOrTransferWithCredits(balanceMovement, transactionApiRequest.getCpfCnpj());
        }
    }

    private void rechargeCredit(BalanceMovement balanceMovement) {
        balanceMovement.setOrigin(balanceMovement.getDestination());
        balanceMovementRepository.save(balanceMovement);
        var creditDestination = creditService.findById(balanceMovement.getDestination().getId());

        var oldBalanceCreditDestination = BigDecimal.valueOf(creditDestination.getBalance());
        var rechargeValue = BigDecimal.valueOf(balanceMovement.getValue());

        creditDestination.setBalance(oldBalanceCreditDestination.add(rechargeValue).doubleValue());
        creditService.save(creditDestination);
    }

    private void payOrTransferWithCredits(BalanceMovement balanceMovement, String cpfCnpj) throws BalanceInsufficientException {
        var creditOrigin = creditService.findById(balanceMovement.getOrigin().getId());
        var creditDestination = getCreditDestination(balanceMovement, cpfCnpj);
        throwExceptionIfThereIsNotOriginAndDestinationCredit(creditOrigin, creditDestination);
        toPay(balanceMovement, creditOrigin, creditDestination);
    }

    private Credit getCreditDestination(BalanceMovement balanceMovement, String cpfCnpj) {
        Optional<Credit> creditDestination = Optional.empty();

        if (TransactionType.PAYMENT.equals(balanceMovement.getType()) && !isNullOrEmpty(cpfCnpj)) {

            creditDestination = ofNullable(creditService.findByCnpj(cpfCnpj));

        } else if (TransactionType.TRANSFER.equals(balanceMovement.getType()) && !isNullOrEmpty(cpfCnpj)) {

            creditDestination = ofNullable(creditService.findByCpf(cpfCnpj));

        } else if (TransactionType.TRANSFER.equals(balanceMovement.getType()) && nonNull(balanceMovement.getDestination())) {

            creditDestination = ofNullable(creditService.findById(balanceMovement.getDestination().getId()));
        }
        return creditDestination.orElse(null);
    }

    private void throwExceptionIfThereIsNotOriginAndDestinationCredit(Credit creditOrigin, Credit creditDestination) {
        if (Objects.isNull(creditOrigin) || isNull(creditDestination))
            throw new IllegalArgumentException();
    }

    private void toPay(BalanceMovement balanceMovement, Credit creditOrigin, Credit creditDestination) {
        validateIfThereIsEnoughBalance(creditOrigin, balanceMovement.getValue());

        balanceMovement.setDestination(creditDestination);
        balanceMovementRepository.save(balanceMovement);

        var valueToPay = BigDecimal.valueOf(balanceMovement.getValue());
        var balanceOrigin = BigDecimal.valueOf(creditOrigin.getBalance());
        var balanceDestination = BigDecimal.valueOf(creditDestination.getBalance());

        creditDestination.setBalance(balanceDestination.add(valueToPay).doubleValue());
        creditOrigin.setBalance(balanceOrigin.subtract(valueToPay).doubleValue());

        creditService.save(creditOrigin);
        creditService.save(creditDestination);
    }

    private void validateIfThereIsEnoughBalance(Credit creditOrigin, Double valueToDebit) {
        if (creditOrigin.getBalance() < valueToDebit)
            throw new BalanceInsufficientException("Saldo infuciente para esta operação");
    }

    public List<BalanceMovement> listAllTransactionsByUserId(Integer userId) {
        return balanceMovementRepository.findByUserId(userId);
    }

    public BalanceMovement getTransactionDetail(Long transactionId) {
        return balanceMovementRepository.findById(transactionId).orElse(null);
    }

    public BalanceMovement getLastTransactionByUserId(Integer userId) {
        var transactions = listAllTransactionsByUserId(userId);
        return transactions.stream().max(Comparator.comparing(BalanceMovement::getId)).orElse(null);
    }

    public BalanceMovement fromDTO(TransactionApiRequest dto) {
        return BalanceMovement.builder()
                .origin(Credit.builder().id(dto.getAccountNumberOrigin()).build())
                .destination(Credit.builder().id(dto.getAccountNumberDestination()).build())
                .value(dto.getValue())
                .type(dto.getType())
                .status(TransactionStatus.APPROVED)
                .operationDate(LocalDateTime.now())
                .build();
    }

    public static TransactionApiResponse toDTO(BalanceMovement balanceMovement) {
        return TransactionApiResponse.builder()
                .id(balanceMovement.getId())
                .value(balanceMovement.getValue())
                .operationDate(balanceMovement.getOperationDate())
                .type(balanceMovement.getType())
                .build();
    }

    public TransactionApiRequest fromScheduleApiRequest(SchedulingApiRequest schedulingApiRequest) {
        return TransactionApiRequest.builder()
                .accountNumberOrigin(schedulingApiRequest.getAccountNumberOrigin())
                .cpfCnpj(schedulingApiRequest.getCpfCnpj())
                .value(schedulingApiRequest.getValue())
                .type(TransactionType.PAYMENT)
                .build();
    }

    public TransactionDetailApiResponse transactionDetailDTO(BalanceMovement balanceMovement) {
        return TransactionDetailApiResponse.builder()
                .id(balanceMovement.getId())
                .origin(balanceMovement.getOrigin().getUser().getName())
                .destination(balanceMovement.getDestination().getUser().getName())
                .value(balanceMovement.getValue())
                .operationDate(balanceMovement.getOperationDate())
                .type(balanceMovement.getType())
                .status(balanceMovement.getStatus())
                .build();
    }
}
