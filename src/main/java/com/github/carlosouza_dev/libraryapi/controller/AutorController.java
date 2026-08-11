package com.github.carlosouza_dev.libraryapi.controller;

import com.github.carlosouza_dev.libraryapi.controller.dto.AutorDTO;
import com.github.carlosouza_dev.libraryapi.model.Autor;
import com.github.carlosouza_dev.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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

    //GET http://localhost:8080/autores/ass-22ljklafa-asdfa
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscar(@PathVariable UUID id){
        Optional<Autor> optAutor = service.buscar(id);

        if(optAutor.isPresent()){
            Autor autor = optAutor.get();

            AutorDTO autorDTO = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );

            return ResponseEntity.ok(autorDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<Void> deletar(@PathVariable UUID id){
        Optional<Autor> optAutor = service.buscar(id);

        if (optAutor.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.deletar(optAutor.get());

        return ResponseEntity.noContent().build();
    }

    //GET http://localhost:8080/autores?nome=loscar
    @GetMapping
    public ResponseEntity<List<AutorDTO>> filtrar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ){
        List<Autor> listAutores = service.filtrar(nome, nacionalidade);

        List<AutorDTO> listAutoresDTO = listAutores.stream()
                .map(autor -> {
                    return new AutorDTO(
                            autor.getId(),
                            autor.getNome(),
                            autor.getDataNascimento(),
                            autor.getNacionalidade()
                    );
                }).toList();

        return ResponseEntity.ok(listAutoresDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable UUID id,
            @RequestBody AutorDTO dto
    ){
        Optional<Autor> optAutor = service.buscar(id);

        if(optAutor.isEmpty()) return ResponseEntity.notFound().build();

        Autor autor = optAutor.get();
        autor.setNome(dto.nome());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}
