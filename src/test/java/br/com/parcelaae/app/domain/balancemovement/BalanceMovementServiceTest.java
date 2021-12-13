package br.com.parcelaae.app.domain.balancemovement;

import br.com.parcelaae.app.domain.balancemovement.model.BalanceMovement;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionApiRequest;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionType;
import br.com.parcelaae.app.domain.balancemovement.repository.BalanceMovementRepository;
import br.com.parcelaae.app.domain.balancemovement.service.BalanceMovementService;
import br.com.parcelaae.app.domain.credit.model.Credit;
import br.com.parcelaae.app.domain.credit.service.CreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BalanceMovementServiceTest {

    private BalanceMovementService balanceMovementService;

    @Mock
    private BalanceMovementRepository balanceMovementRepository;
    @Mock
    private CreditService creditService;

    @BeforeEach
    void setUp() {
        balanceMovementService = new BalanceMovementService(balanceMovementRepository, creditService);
    }

    @Test
    void shouldSaveTransactionOfTypeRecharge() {
        var transactionApiRequest = TransactionApiRequest.builder()
                .type(TransactionType.RECHARGE)
                .accountNumberOrigin(1L)
                .accountNumberDestination(2L)
                .value(150.00)
                .build();
        var creditDestination = Credit.builder()
                .id(2L)
                .balance(100.00)
                .build();
        var balanceMovement = BalanceMovement.builder()
                .destination(creditDestination)
                .build();

        when(creditService.findById(anyLong())).thenReturn(creditDestination);

        balanceMovementService.save(transactionApiRequest);

        assertThat(creditDestination.getBalance()).isEqualTo(250.00);

        verify(balanceMovementRepository, times(1)).save(any(BalanceMovement.class));
        verify(creditService, times(1)).save(creditDestination);
    }
}
