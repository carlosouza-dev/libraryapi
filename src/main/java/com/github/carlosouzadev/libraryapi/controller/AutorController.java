package com.github.carlosouzadev.libraryapi.controller;

import com.github.carlosouzadev.libraryapi.controller.dto.AutorDTO;
import com.github.carlosouzadev.libraryapi.controller.mapper.AutorMapper;
import com.github.carlosouzadev.libraryapi.model.Autor;
import com.github.carlosouzadev.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Autor> salvar(@RequestBody @Valid AutorDTO autorDTO) {

        Autor autor = mapper.toEntity(autorDTO);
        service.salvar(autor);

        URI uri = geralHeaderLocation(autor.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable String id,
            @RequestBody @Valid AutorDTO dto
    ) {
        UUID idAutor = UUID.fromString(id);

        Optional<Autor> opt = service.buscar(idAutor);

        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        // Aqui é importante NÃO usar mapper pois ele irá sobrescrever (colocando null)
        // todos os campos que não existirem no DTO
        Autor autor = opt.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> buscar(@PathVariable UUID id) {
        Autor autor = service.buscar(id).orElse(null);

        if (autor == null) {
            return ResponseEntity.notFound().build();
        }

        AutorDTO dto = mapper.toDTO(autor);

        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    public ResponseEntity<List<AutorDTO>> filtrar(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "nacionalidade", required = false) String nacionalidade
    ) {
        List<Autor> autores = service.filtrarByExample(nome, nacionalidade);

        List<AutorDTO> list = autores.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(list);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        Optional<Autor> opt = service.buscar(UUID.fromString(id));

        if (opt.isEmpty()) return ResponseEntity.noContent().build();

        service.deletar(opt.get());

        return ResponseEntity.noContent().build();
    }
}
