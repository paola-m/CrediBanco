package com.crediBanco.crediBancoCard.service;

import com.crediBanco.crediBancoCard.dto.CardDto;
import com.crediBanco.crediBancoCard.dto.CardResponse;
import com.crediBanco.crediBancoCard.dto.MessageResponse;
import com.crediBanco.crediBancoCard.model.Card;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CardService {
     Card getCard(String cardId);
     CardDto getCards (String productId);
     ResponseEntity<CardResponse> activateCard (String cardId);
     ResponseEntity<CardResponse> deleteCard (String cardId);
     ResponseEntity<MessageResponse> rechargeCredit (String cardId, String balance);
     ResponseEntity<String> skipConsultation (String cardId);
     void validationCreditCard (Integer saldo, Integer transactionValue);
     void  validationActiveCard(Integer isActive);
     CardResponse buildResponseCard(Card card, String message);
     void validationNullCard(Optional<Card> value, String nameField);
}
