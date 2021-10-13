package br.com.parcelaae.app.services;

import br.com.parcelaae.app.domain.BalanceMovement;
import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.domain.enums.TransactionStatus;
import br.com.parcelaae.app.dto.NewTransactionDTO;
import br.com.parcelaae.app.dto.TransactionDTO;
import br.com.parcelaae.app.dto.TransactionDetailDTO;
import br.com.parcelaae.app.repositories.BalanceMovementRepository;
import br.com.parcelaae.app.services.exceptions.BalanceInsufficientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class BalanceMovementService {

    @Autowired
    private BalanceMovementRepository balanceMovementRepository;

    @Autowired
    private CreditService creditService;

    @Autowired
    private UserService userService;

    public void save(BalanceMovement balanceMovement) {

        switch (balanceMovement.getType()) {
            case RECHARGE:
                rechargeCredit(balanceMovement);
                break;
            case PAYMENT:
                payWithCredits(balanceMovement);
                break;
            case TRANSFER:
                tranferCredits(balanceMovement);
                break;
            default:
                log.info("Tipo de operação inválida, " + balanceMovement.getType());
        }
    }

    private void rechargeCredit(BalanceMovement balanceMovement) {
        balanceMovementRepository.save(balanceMovement);
        var creditDestination = creditService.findById(balanceMovement.getDestination().getId());

        var oldBalanceCreditDestination = BigDecimal.valueOf(creditDestination.getBalance());
        var rechargeValue = BigDecimal.valueOf(balanceMovement.getValue());

        creditDestination.setBalance(oldBalanceCreditDestination.add(rechargeValue).doubleValue());
        creditService.save(creditDestination);
    }

    private void payWithCredits(BalanceMovement balanceMovement) throws BalanceInsufficientException {
        toPay(balanceMovement);
    }

    private void tranferCredits(BalanceMovement balanceMovement) throws BalanceInsufficientException {
        toPay(balanceMovement);
    }

    private void toPay(BalanceMovement balanceMovement) {
        var creditOrigin = creditService.findById(balanceMovement.getOrigin().getId());
        var creditDestination = creditService.findById(balanceMovement.getDestination().getId());

        validateIfThereIsEnoughBalance(creditOrigin, balanceMovement.getValue());

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

    public BalanceMovement fromDTO(NewTransactionDTO dto) {
        return BalanceMovement.builder()
                .origin(Credit.builder().id(dto.getOriginCreditId()).build())
                .destination(Credit.builder().id(dto.getDestinationCreditId()).build())
                .value(dto.getValue())
                .type(dto.getType())
                .status(TransactionStatus.APPROVED)
                .operationDate(LocalDateTime.now())
                .build();
    }

    public static TransactionDTO toDTO(BalanceMovement balanceMovement) {
        return TransactionDTO.builder()
                .id(balanceMovement.getId())
                .value(balanceMovement.getValue())
                .operationDate(balanceMovement.getOperationDate())
                .type(balanceMovement.getType())
                .build();
    }

    public TransactionDetailDTO transactionDetailDTO(BalanceMovement balanceMovement) {
        return TransactionDetailDTO.builder()
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
