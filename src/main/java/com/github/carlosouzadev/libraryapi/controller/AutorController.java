package com.github.carlosouzadev.libraryapi.controller;

import com.github.carlosouzadev.libraryapi.controller.dto.AutorDTO;
import com.github.carlosouzadev.libraryapi.model.Autor;
import com.github.carlosouzadev.libraryapi.repository.AutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("autores")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @PostMapping
    public ResponseEntity<Autor> salvar(@RequestBody @Valid AutorDTO autor){

        var autorEntity = new Autor();
        autorEntity.setNome(autor.nome());
        autorEntity.setNacionalidade(autor.nacionalidade());
        autorEntity.setDataNascimento(autor.dataNascimento());

        Autor save = repository.save(autorEntity);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Autor> buscar(@PathVariable UUID id){
        Autor autor = repository.findById(id).orElse(null);

        if (autor == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(autor);
    }

    @GetMapping()
    public ResponseEntity<List<AutorDTO>> buscarTodos(){
        List<Autor> autores = repository.findAll();

        List<AutorDTO> list = autores.stream()
                .map(autor -> {
                    return new AutorDTO(
                            autor.getId(),
                            autor.getNome(),
                            autor.getDataNascimento(),
                            autor.getNacionalidade()
                    );
                })
                .toList();

        return ResponseEntity.ok(list);
    }
}
