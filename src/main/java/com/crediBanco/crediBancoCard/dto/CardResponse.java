package com.crediBanco.crediBancoCard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {

    private String message;

    private BigInteger cardId;
    private Integer productId ;
    private String holderName ;
    private String expirationDate;
    private Integer isActive ;
    private Integer isBlocked;
    private Integer balance;
}
