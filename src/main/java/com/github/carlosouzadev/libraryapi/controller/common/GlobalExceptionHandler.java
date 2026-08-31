package com.github.carlosouzadev.libraryapi.controller.common;

import com.github.carlosouzadev.libraryapi.controller.dto.ErroCampo;
import com.github.carlosouzadev.libraryapi.controller.dto.ErroResposta;
import com.github.carlosouzadev.libraryapi.exception.OperacaoNaoPermitidaException;
import com.github.carlosouzadev.libraryapi.exception.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Excecoes lancada pelo starter validation
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

    // Exceções de regras de negócios
    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErroResposta> handleRegistroDuplicadoException(RegistroDuplicadoException e){
        ErroResposta erro = ErroResposta.conflito(e.getMessage());

        return ResponseEntity.status(erro.status()).body(erro);
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<ErroResposta> handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        ErroResposta erro = ErroResposta.erroPadrao(e.getMessage());

        return ResponseEntity.status(erro.status()).body(erro);
    }

    // Exceções não tratadas
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> handleRuntimeException(RuntimeException e){
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado",
                List.of()
        );
        return ResponseEntity.status(erro.status()).body(erro);
    }
}
