package com.github.carlosouza_dev.libraryapi.controller.common;

import com.github.carlosouza_dev.libraryapi.controller.dto.ErroCampo;
import com.github.carlosouza_dev.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleMethodArgumentNotValidateException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ErroCampo> listErroCampo = fieldErrors.stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();


        ErroResposta erroResposta = new ErroResposta(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Erro de validação",
                listErroCampo
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(erroResposta);
    }
}
