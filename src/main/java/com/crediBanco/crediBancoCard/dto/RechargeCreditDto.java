package com.crediBanco.crediBancoCard.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;




@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Valid
public class RechargeCreditDto {
    private @NotNull(message = "The cardId field cannot be null")
    String cardId;

    private @NotNull (message = "The balance field cannot be null")
    String balance;
}
