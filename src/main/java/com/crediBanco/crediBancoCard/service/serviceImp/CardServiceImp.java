package com.crediBanco.crediBancoCard.service.serviceImp;

import com.crediBanco.crediBancoCard.dto.CardDto;
import com.crediBanco.crediBancoCard.dto.CardResponse;
import com.crediBanco.crediBancoCard.dto.MessageResponse;
import com.crediBanco.crediBancoCard.dto.errors.SystemException;
import com.crediBanco.crediBancoCard.model.Card;
import com.crediBanco.crediBancoCard.repository.CardRepository;
import com.crediBanco.crediBancoCard.service.CardService;
import com.crediBanco.crediBancoCard.utils.Constans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CardServiceImp implements CardService {

    @Autowired
    CardRepository cardRepository;



    public Card getCard(String cardId){
        Optional<Card> card = cardRepository.findById( new BigInteger(cardId));
        this.validationNullCard(card, "cardId");

        return card.get();
    }


    @Override
    public CardDto getCards(String productId) {

        String numerousOratorios = new Random()
                .ints(10, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
        String numCard = productId.concat(numerousOratorios);

        this.createCard(new BigInteger(numCard), Integer.parseInt(productId));

        return Objects.requireNonNull(ResponseEntity.ok(CardDto.builder().cardId(numCard)).getBody()).build();

    }

    @Override
    public ResponseEntity<CardResponse> activateCard(String cardId) {

        Card cardModel = this.getCard(cardId);
        cardModel.setIsActive(1);
        cardModel.setExpirationDate(this.addThreeYears());
        cardRepository.save(cardModel);

        return ResponseEntity.ok(this.buildResponseCard(cardModel, "UPDATE: CARD ACTIVATED"));
    }

    @Override
    public ResponseEntity<CardResponse> deleteCard(String cardId) {

        Card detectiveCard = this.getCard(cardId);
        detectiveCard.setIsActive(0);
        cardRepository.save(detectiveCard);

        return ResponseEntity.ok(this.buildResponseCard(detectiveCard, "DELETE: CARD DEACTIVATED"));
    }

    @Override
    public ResponseEntity<MessageResponse> rechargeCredit(String cardId, String balance) {

        Card updateCredit = this.getCard(cardId);
        this.validationActiveCard(updateCredit.getIsActive());

        int newBalance = updateCredit.getBalance() + Integer.parseInt(balance);

        updateCredit.setBalance(Integer.parseInt(String.valueOf(newBalance)));

        cardRepository.save(updateCredit);

        return ResponseEntity.ok(MessageResponse.builder().message("UPDATE: RECHARGED CREDIT CON:"+balance
                +" BALANCE TOTAL: "+newBalance).build());
    }

    @Override
    public ResponseEntity<String> skipConsultation(String cardId) {
        Card consult = this.getCard(cardId);

        return ResponseEntity.ok("Your credit is: "+consult.getBalance());
    }


    private void createCard(BigInteger numCard, Integer productId){
        Card card = Card.builder()
                .cardId(numCard)
                .productId(productId)
                .holderName("N.N")
                .expirationDate(this.addThreeYears())
                .isActive(0)
                .isBlockend(0)
                .balance(00000)
                .build();
        cardRepository.save(card);
    }


    private String addThreeYears(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 3);
        // Obtener el año y el mes
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Sumar 1 al mes, ya que se indexa desde 0

        // Formatear el resultado
        return String.format("%02d/%d", month, year);
    }

    @Override
    public void validationCreditCard (Integer saldo, Integer transactionValue){
        if(saldo==0 || saldo< transactionValue){
            throw SystemException.builder()
                    .code(Constans.LOG_ERROR_003)
                    .description(Constans.LOG_ERROR_003_DESCRIPTION)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .tecError(Constans.VALIDATION_ERROR)
                    .build();
        }
    }
    @Override
    public void  validationActiveCard(Integer isActive){
        if(isActive.equals(0)){
            throw SystemException.builder()
                    .code(Constans.LOG_ERROR_004)
                    .description(Constans.LOG_ERROR_004_DESCRIPTION)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constans.VALIDATION_ERROR)
                    .build();
        }
    }
    @Override
    public CardResponse buildResponseCard(Card card, String message){

        return CardResponse.builder()
                .message(message)
                .cardId(card.getCardId())
                .productId(card.getProductId())
                .holderName(card.getHolderName())
                .expirationDate(card.getExpirationDate())
                .isActive(card.getIsActive())
                .balance(card.getBalance())
                .build();
    }

    @Override
    public void validationNullCard(Optional<Card> value, String nameField){
        if(value.isEmpty()){
            throw SystemException.builder()
                    .code(Constans.LOG_ERROR_002)
                    .description(nameField+Constans.LOG_ERROR_002_DESCRIPTION)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .tecError(Constans.VALIDATION_ERROR)
                    .build();
        }
    }

}
