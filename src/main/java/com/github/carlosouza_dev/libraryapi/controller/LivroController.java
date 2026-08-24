package com.github.carlosouza_dev.libraryapi.controller;

import com.github.carlosouza_dev.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.carlosouza_dev.libraryapi.controller.dto.ErroResposta;
import com.github.carlosouza_dev.libraryapi.exception.RegistroDuplicadoException;
import com.github.carlosouza_dev.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto){
        try {
            return ResponseEntity.ok().body(dto);
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
    public ResponseEntity<?> buscar(){
        return ResponseEntity.noContent().build();
    }
}
