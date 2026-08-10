package com.github.carlosouza_dev.libraryapi.controller;

import com.github.carlosouza_dev.libraryapi.controller.dto.AutorDTO;
import com.github.carlosouza_dev.libraryapi.model.Autor;
import com.github.carlosouza_dev.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor autorEntity = autor.mapearParaAutor();

        service.salvar(autorEntity);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntity.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscar(@PathVariable UUID id){
        Optional<Autor> optAutor = service.buscar(id);

        if(optAutor.isPresent()){
            return ResponseEntity.ok(optAutor.get());
        }

        return ResponseEntity.notFound().build();

    }
}
