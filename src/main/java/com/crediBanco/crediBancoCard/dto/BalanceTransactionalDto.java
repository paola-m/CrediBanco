package com.crediBanco.crediBancoCard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
@Valid
public class BalanceTransactionalDto {

    private @NotNull(message = "The cardId field cannot be null")
    String cardId;

    private @NotNull(message = "The price field cannot be null")
    Integer price;
}
