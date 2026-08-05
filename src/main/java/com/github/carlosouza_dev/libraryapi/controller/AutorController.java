package com.github.carlosouza_dev.libraryapi.controller;

import com.github.carlosouza_dev.libraryapi.controller.dto.AutorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autores")
public class AutorController {

    @PostMapping
    public ResponseEntity salvar(AutorDTO autor){

        // salvar

        return new ResponseEntity("Autor " + autor + " salvo com sucesso", HttpStatus.valueOf(200));
    }
}
