package br.com.parcelaae.app.controllers;

import br.com.parcelaae.app.core.exception.AuthorizationException;
import br.com.parcelaae.app.domain.balancemovement.model.BalanceMovement;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionApiRequest;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionApiResponse;
import br.com.parcelaae.app.domain.balancemovement.model.TransactionDetailApiResponse;
import br.com.parcelaae.app.domain.balancemovement.service.BalanceMovementService;
import br.com.parcelaae.app.domain.security.SecurityUtil;
import br.com.parcelaae.app.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class BalanceMovementControllerTest {

    public static final String AUTHORIZATION_EXCEPTION_MESSAGE = "Acesso negado";
    private BalanceMovementController balanceMovementController;

    @Mock
    private BalanceMovementService balanceMovementService;

    @BeforeEach
    void setUp() {
        balanceMovementController = new BalanceMovementController(balanceMovementService);
    }

    @Test
    void shouldInsertNewBalanceMovement() {
        var transactionApiRequest = TransactionApiRequest.builder().build();

        var response = balanceMovementController.insert(transactionApiRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(balanceMovementService, times(1)).save(any(TransactionApiRequest.class));
    }

    @Test
    void shouldGetTransactionDetail() {
        var transactionId = 1L;
        var balanceMovement = BalanceMovement.builder().id(transactionId).build();
        var apiResponseExpected = TransactionDetailApiResponse.builder()
                .id(transactionId).build();

        when(balanceMovementService.getTransactionDetail(transactionId)).thenReturn(balanceMovement);
        when(balanceMovementService.transactionDetailDTO(balanceMovement)).thenReturn(apiResponseExpected);

        var response = balanceMovementController.getTransactionDetail(transactionId);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(apiResponseExpected);
    }

    @Test
    void shouldListAllTransactionsByUserId() {
        var userId = 1;
        var balanceMovement = BalanceMovement.builder().build();
        var balanceMovements = Collections.singletonList(balanceMovement);
        var transaction = TransactionApiResponse.builder().build();
        var transactionsExpected = Collections.singletonList(transaction);

        try(MockedStatic<UserService> userServiceMockedStatic = Mockito.mockStatic(UserService.class)) {
            when(balanceMovementService.listAllTransactionsByUserId(userId)).thenReturn(balanceMovements);

            var response = balanceMovementController.listAllTransactionsByUserId(userId);

            assertThat(response.getStatusCode()).isEqualTo(OK);
            assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(transactionsExpected);
        }
    }

    @Test
    void shouldThrowAuthorizationExceptionWhenListAllTransactionsByUserId() {
        var userId = 1;

        var authorizationException = catchThrowableOfType(
                () -> balanceMovementController.listAllTransactionsByUserId(userId),
                AuthorizationException.class);

        assertThat(authorizationException)
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining(AUTHORIZATION_EXCEPTION_MESSAGE);
    }
}