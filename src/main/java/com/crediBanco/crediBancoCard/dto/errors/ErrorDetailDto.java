package com.crediBanco.crediBancoCard.dto.errors;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorDetailDto implements Serializable{

    private static final Long ID = 1L;
    private String code;
    private String description;
    private String tecError;
}
