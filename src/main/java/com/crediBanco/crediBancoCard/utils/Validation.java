package com.crediBanco.crediBancoCard.utils;

import com.crediBanco.crediBancoCard.dto.errors.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Validation {

    public void validationProductIdCharacters(String productId, String nameField){
        if(!(productId.length()==6 && productId.matches("\\d+"))){
            throw SystemException.builder()
                    .code(Constans.LOG_ERROR_001)
                    .description(Constans.LOG_ERROR_001_DESCRIPTION+nameField)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .tecError(Constans.VALIDATION_ERROR)
                    .build();
        }
    }
}
