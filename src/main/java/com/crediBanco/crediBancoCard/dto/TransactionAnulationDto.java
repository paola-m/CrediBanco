package com.crediBanco.crediBancoCard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAnulationDto {

    private @NotNull(message = "The cardId field cannot be null")
    String cardId;

    private @NotNull (message = "The transactionId field cannot be null")
    Integer transactionId;
}
