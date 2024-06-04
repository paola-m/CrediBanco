package com.crediBanco.crediBancoCard.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemException extends RuntimeException{

    private static final Long ID = 1L;
    private String code;
    private String description;
    private HttpStatus httpStatus;
    private String tecError;
}
