package com.crediBanco.crediBancoCard.controller.advice;

import com.crediBanco.crediBancoCard.dto.errors.ErrorDetailDto;
import com.crediBanco.crediBancoCard.dto.errors.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(SystemException.class)
    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<ErrorDetailDto> resourceNotFoundException(SystemException ex) {

        return ResponseEntity.status(ex.getHttpStatus())
                .body(ErrorDetailDto.builder()
                        .code(ex.getCode())
                        .description(ex.getDescription())
                        .tecError(ex.getTecError())
                        .build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDetailDto> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    private List<ErrorDetailDto> processFieldErrors(List<FieldError> fieldErrors) {
        List<ErrorDetailDto> errores = new ArrayList<>();
        for (FieldError fieldError: fieldErrors) {
            errores.add(ErrorDetailDto.builder()
                    .code(fieldError.getField())
                    .description(fieldError.getDefaultMessage())
                    .tecError("VALIDATION_ERROR")
                    .build());
        }
        return errores;
    }
}
