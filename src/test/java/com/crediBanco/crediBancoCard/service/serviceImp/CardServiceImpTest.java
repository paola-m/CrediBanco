package com.crediBanco.crediBancoCard.service.serviceImp;

import com.crediBanco.crediBancoCard.dto.CardResponse;
import com.crediBanco.crediBancoCard.dto.errors.SystemException;
import com.crediBanco.crediBancoCard.model.Card;
import com.crediBanco.crediBancoCard.repository.CardRepository;
import com.crediBanco.crediBancoCard.utils.Constans;
import org.glassfish.jaxb.core.v2.model.core.ID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CardServiceImpTest {
    @Mock
    CardRepository cardRepository;
    @InjectMocks
    CardServiceImp cardServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCard() {

        Card mockCard = new Card(new BigInteger("1234560000000001"), 123456, "John Doe", "2027/05", 1, 0, 1000);
        when(cardRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(mockCard));

        Card result = cardServiceImp.getCard("1234560000000001");

        Assertions.assertNotNull(result);
        assertEquals(mockCard.getCardId(), result.getCardId());
        assertEquals(mockCard.getHolderName(), result.getHolderName());
        assertEquals(mockCard.getExpirationDate(), result.getExpirationDate());
        assertEquals(mockCard.getBalance(), result.getBalance());
    }


    @Test
    void testActivateCard() {
        Card cardResponse = new Card();
        cardResponse.setIsActive(1);
        cardResponse.setBalance(500);

        when(cardRepository.save(any())).thenReturn(cardResponse);

        when(cardRepository.findById(any())).thenReturn(Optional.of(cardResponse));

        ResponseEntity<CardResponse> result = cardServiceImp.activateCard("0245421210");

        assertNotNull(result);
    }

    @Test
    void testValidationNullCard() {
        CardServiceImp cardService = new CardServiceImp();
        Optional<Card> emptyOptional = Optional.empty();

        when(cardRepository.findById(any(ID.class))).thenReturn(emptyOptional);

        try {
            cardService.validationNullCard(emptyOptional, "testField");
            fail("Expected SystemException was not thrown");
        } catch (SystemException e) {
            assertEquals(Constans.LOG_ERROR_002, e.getCode());
            assertEquals("testField" + Constans.LOG_ERROR_002_DESCRIPTION, e.getDescription());
            assertEquals(HttpStatus.NOT_FOUND, e.getHttpStatus());
            assertEquals(Constans.VALIDATION_ERROR, e.getTecError());
        }
    }
}
