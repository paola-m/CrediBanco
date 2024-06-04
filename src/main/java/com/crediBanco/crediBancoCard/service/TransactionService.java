package com.crediBanco.crediBancoCard.service;

import com.crediBanco.crediBancoCard.dto.MessageResponse;
import com.crediBanco.crediBancoCard.dto.TransactionResponse;
import org.springframework.http.ResponseEntity;


public interface TransactionService {

    ResponseEntity<TransactionResponse> balanceTransactional (String cardId, Integer price);
    ResponseEntity<TransactionResponse> getTransaction (String transactionId);
    ResponseEntity<MessageResponse>  annulTransaction(Integer transactionId);
}
