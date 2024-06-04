package com.crediBanco.crediBancoCard.controller;

import com.crediBanco.crediBancoCard.dto.*;
import com.crediBanco.crediBancoCard.service.CardService;
import com.crediBanco.crediBancoCard.utils.ConvertJson;
import com.crediBanco.crediBancoCard.utils.Validation;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/card")
public class Card {

    @Autowired
    CardService cardService;
    @Autowired
    ConvertJson convertJson;
    @Autowired
    Validation validation;


    @GetMapping(value="/{productId}/number")
    public CardDto generateCardNumber(@Valid @PathVariable("productId") String productId) {
        validation.validationProductIdCharacters(productId, "productId");
        return cardService.getCards(productId);
    }

    @PostMapping("/enroll")
    public ResponseEntity<CardResponse> activateCard (@Valid @RequestBody  CardDto request) throws JsonProcessingException {
        return cardService.activateCard(request.getCardId());
    }

    @PostMapping("/balance")
    public ResponseEntity<MessageResponse> rechargeCredit (@Valid @RequestBody RechargeCreditDto rechargeCreditDto){

        return cardService.rechargeCredit(rechargeCreditDto.getCardId(), rechargeCreditDto.getBalance());
    }

    @GetMapping(value="/balance/{cardId}")
    public ResponseEntity<String> getCredit(@Valid @PathVariable("cardId") String cardId) {
        return cardService.skipConsultation(cardId);
    }


    @DeleteMapping(value="/{cardId}")
    public ResponseEntity<CardResponse> deleteCard (@Valid @PathVariable("cardId") String cardId) {
        return cardService.deleteCard(cardId);
    }

}
