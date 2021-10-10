package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.controllers.exceptions.BalanceInsufficientException;
import br.com.parcelaae.app.dto.NewTransactionDTO;
import br.com.parcelaae.app.dto.TransactionDTO;
import br.com.parcelaae.app.dto.TransactionDetailDTO;
import br.com.parcelaae.app.services.BalanceMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class BalanceMovementController {

    @Autowired
    private BalanceMovementService balanceMovementService;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody NewTransactionDTO newTransactionDTO) throws BalanceInsufficientException {
        var newTransaction = balanceMovementService.fromDTO(newTransactionDTO);
        balanceMovementService.save(newTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailDTO> getTransactionDetail(@PathVariable("transactionId") Long transactionId) {
        var balanceMovement = balanceMovementService.getTransactionDetail(transactionId);
        return ResponseEntity.ok(balanceMovementService.transactionDetailDTO(balanceMovement));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<TransactionDTO>> listAllTransactionsByUserId(@PathVariable("userId") Integer userId) {
        var transactions = balanceMovementService.listAllTransactionsByUserId(userId);
        var transactionsDTO = transactions.stream().map(BalanceMovementService::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(transactionsDTO);
    }
}
