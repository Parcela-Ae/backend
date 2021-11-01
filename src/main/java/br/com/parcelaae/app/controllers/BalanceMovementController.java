package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.domain.balancemovement.model.TransactionApiRequest;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionApiResponse;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionDetailApiResponse;
import br.com.parcelaae.app.domain.balancemovement.service.BalanceMovementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.parcelaae.app.domain.user.service.UserService.validateIfUserHasAuthoritation;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/transactions")
public class BalanceMovementController {

    private final BalanceMovementService balanceMovementService;

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody TransactionApiRequest transactionApiRequest) {
        balanceMovementService.save(transactionApiRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDetailApiResponse> getTransactionDetail(@PathVariable("transactionId") Long transactionId) {
        var balanceMovement = balanceMovementService.getTransactionDetail(transactionId);
        return ResponseEntity.ok(balanceMovementService.transactionDetailDTO(balanceMovement));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<TransactionApiResponse>> listAllTransactionsByUserId(@PathVariable("userId") Integer userId) {
        validateIfUserHasAuthoritation(userId);
        var transactions = balanceMovementService.listAllTransactionsByUserId(userId);
        var transactionsDTO = transactions.stream().map(BalanceMovementService::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(transactionsDTO);
    }
}
