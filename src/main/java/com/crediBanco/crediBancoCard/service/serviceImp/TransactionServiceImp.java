package com.crediBanco.crediBancoCard.service.serviceImp;

import com.crediBanco.crediBancoCard.dto.MessageResponse;
import com.crediBanco.crediBancoCard.dto.TransactionResponse;
import com.crediBanco.crediBancoCard.dto.errors.SystemException;
import com.crediBanco.crediBancoCard.model.Card;
import com.crediBanco.crediBancoCard.model.Transaction;
import com.crediBanco.crediBancoCard.repository.CardRepository;
import com.crediBanco.crediBancoCard.repository.TransactionRepository;
import com.crediBanco.crediBancoCard.service.CardService;
import com.crediBanco.crediBancoCard.service.TransactionService;
import com.crediBanco.crediBancoCard.utils.Constans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionServiceImp implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CardService cardService;


    @Override
    public ResponseEntity<TransactionResponse> balanceTransactional(String cardId, Integer price) {
        Transaction transaction = transactionRepository.save(
                Transaction.builder()
                        .transactionType("Advance")
                        .amount(price)
                        .transactionDate(new Date())
                        .cardId(cardService.getCard(cardId))
                        .status("Completed")
                        .build());
        this.subtractCreditCard(cardId, price);

        return ResponseEntity.ok(this.buildResponseTransaction(transaction, cardId));
    }

    @Override
    public ResponseEntity<TransactionResponse> getTransaction(String transactionId) {
        Optional<Transaction> transactionConsult = transactionRepository.findById( new BigInteger(transactionId));
        this.validationNullTransaction(transactionConsult,"transactionId");

        return ResponseEntity.ok(this.buildResponseTransaction(
                transactionConsult.get(),
                String.valueOf(transactionConsult.get().getCardId().getCardId())));
    }


    private TransactionResponse buildResponseTransaction (Transaction transaction, String cardId){
        return TransactionResponse.builder()
                .message("TRANSACTION COMPLETED")
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .time(transaction.getTransactionDate())
                .status(transaction.getStatus())
                .cardId(new BigInteger(cardId))
                .build();
    }

    private void subtractCreditCard( String cardId,Integer transactionValue){
        Card card = cardService.getCard(cardId);
        cardService.validationActiveCard(card.getIsActive());
    }


    public ResponseEntity<MessageResponse> annulTransaction(Integer transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);

        this.validationNullTransaction(optionalTransaction, "TransactionId");
        this.validationTimeTransaction(optionalTransaction.get());

        Transaction transaction = optionalTransaction.get();

        transaction.setStatus("annulled");
        transactionRepository.save(transaction);

        Card card = transaction.getCardId();
        card.setBalance(card.getBalance() + transaction.getAmount());
        cardRepository.save(card);
        return ResponseEntity.ok(MessageResponse.builder()
                .message("Transaction cannot be annulled, it's older than 24 hours").build());
    }

    private void validationNullTransaction(Optional<Transaction> value, String nameField){
        if(value.isEmpty()){
            throw SystemException.builder()
                    .code(Constans.LOG_ERROR_002)
                    .description(nameField+Constans.LOG_ERROR_002_DESCRIPTION)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .tecError(Constans.VALIDATION_ERROR)
                    .build();
        }
    }

    private void validationTimeTransaction(Transaction transaction){
        Date now = new Date();
        long diffInMillies = Math.abs(now.getTime() - transaction.getTransactionDate().getTime());
        long diffInHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (diffInHours > 24) {
            throw SystemException.builder()
                    .code(Constans.LOG_ERROR_002)
                    .description("Transaction cannot be annulled, it's older than 24 hours. ")
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .tecError("TRANSATION DATE: "+  transaction.getTransactionDate())
                    .build();
        }
    }

}
