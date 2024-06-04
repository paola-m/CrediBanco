package com.crediBanco.crediBancoCard.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String message;

    private Integer transactionId;
    private String transactionType;
    private Integer amount;
    private Date time;
    private String status;
    private BigInteger cardId;
}
