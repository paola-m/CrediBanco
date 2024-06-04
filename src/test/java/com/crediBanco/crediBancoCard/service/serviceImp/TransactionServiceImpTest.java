package com.crediBanco.crediBancoCard.service.serviceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.crediBanco.crediBancoCard.model.Transaction;
import com.crediBanco.crediBancoCard.model.Card;
import com.crediBanco.crediBancoCard.repository.TransactionRepository;
import com.crediBanco.crediBancoCard.dto.TransactionResponse;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceImpTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImp transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTransaction_ShouldReturnTransactionResponse_WhenTransactionExists() {
        Card card = new Card();
        card.setCardId(new BigInteger("1234567890123456"));
        Transaction transaction = new Transaction();
        transaction.setTransactionId(3);
        transaction.setCardId(card);
        transaction.setTransactionId(525518);


        when(transactionRepository.findById(any(BigInteger.class)))
                .thenReturn(Optional.of(transaction));

        ResponseEntity<TransactionResponse> response =
                transactionService.getTransaction(transaction.getTransactionId().toString());
        assertNotNull(response);
    }

    @Test
    void getTransaction_ShouldThrowException_WhenTransactionDoesNotExist() {

        BigInteger transactionId = new BigInteger("1234560000000001");
        when(transactionRepository.findById(any(BigInteger.class)))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            transactionService.getTransaction(transactionId.toString());
        });
    }
}
