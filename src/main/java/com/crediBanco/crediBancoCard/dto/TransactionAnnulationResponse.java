package com.crediBanco.crediBancoCard.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAnnulationResponse {
    private String message;

    private Integer transactionId;
    private String transactionType;
    private Integer amount;
    private Date time;
    private String status;
    private CardResponse card;
}
