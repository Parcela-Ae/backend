package br.com.parcelaae.app.services;

import br.com.parcelaae.app.controllers.exceptions.BalanceInsufficientException;
import br.com.parcelaae.app.domain.BalanceMovement;
import br.com.parcelaae.app.domain.Credit;
import br.com.parcelaae.app.domain.enums.TransactionStatus;
import br.com.parcelaae.app.dto.NewTransactionDTO;
import br.com.parcelaae.app.dto.TransactionDTO;
import br.com.parcelaae.app.dto.TransactionDetailDTO;
import br.com.parcelaae.app.repositories.BalanceMovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class BalanceMovementService {

    @Autowired
    private BalanceMovementRepository balanceMovementRepository;

    @Autowired
    private CreditService creditService;

    public void save(BalanceMovement balanceMovement) throws BalanceInsufficientException {

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
        creditDestination.setBalance(balanceMovement.getValue());
        creditService.save(creditDestination);
    }

    private void payWithCredits(BalanceMovement balanceMovement) throws BalanceInsufficientException {
        toPay(balanceMovement);
    }

    private void tranferCredits(BalanceMovement balanceMovement) throws BalanceInsufficientException {
        toPay(balanceMovement);
    }

    private void toPay(BalanceMovement balanceMovement) throws BalanceInsufficientException {
        var creditOrigin = creditService.findById(balanceMovement.getOrigin().getId());
        var creditDestination = creditService.findById(balanceMovement.getDestination().getId());

        validateIfThereIsEnoughBalance(creditOrigin, balanceMovement.getValue());

        balanceMovementRepository.save(balanceMovement);
        creditDestination.setBalance(balanceMovement.getValue());
        creditService.save(creditDestination);
    }

    private void validateIfThereIsEnoughBalance(Credit creditOrigin, Double valueToDebit) throws BalanceInsufficientException {
        if (creditOrigin.getBalance() < valueToDebit)
            throw new BalanceInsufficientException("Saldo infuciente para esta operação");
    }

    public List<BalanceMovement> listAllTransactionsByUserId(Integer userId) {
        return balanceMovementRepository.findByUserId(userId);
    }

    public BalanceMovement getTransactionDetail(Long transactionId) {
        return balanceMovementRepository.findById(transactionId).orElse(null);
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
                .build();
    }

    public TransactionDetailDTO transactionDetailDTO(BalanceMovement balanceMovement) {
        return TransactionDetailDTO.builder()
                .id(balanceMovement.getId())
                .origin(balanceMovement.getOrigin().getUser().getName())
                .destination(balanceMovement.getDestination().getUser().getName())
                .value(balanceMovement.getValue())
                .type(balanceMovement.getType())
                .status(balanceMovement.getStatus())
                .build();
    }
}
