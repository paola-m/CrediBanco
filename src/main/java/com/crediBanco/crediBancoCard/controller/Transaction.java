package com.crediBanco.crediBancoCard.controller;

import com.crediBanco.crediBancoCard.dto.*;
import com.crediBanco.crediBancoCard.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/card")
public class Transaction {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/purchase")
    public ResponseEntity<TransactionResponse> balanceTransactional (
            @Valid @RequestBody BalanceTransactionalDto request) {

        return transactionService.balanceTransactional(
                request.getCardId(),
                request.getPrice());
    }

    @GetMapping(value="/transaction/{transactionId}")
    public ResponseEntity<TransactionResponse> getTransaction(@Valid @PathVariable("transactionId") String transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @PostMapping("/transaction/anulation")
    public ResponseEntity<MessageResponse> transactionAnulation (
            @Valid @RequestBody TransactionAnulationDto request) {

        return transactionService.annulTransaction(request.getTransactionId());
    }
}
