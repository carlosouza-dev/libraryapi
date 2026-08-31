package com.github.carlosouzadev.libraryapi.controller;

import com.github.carlosouzadev.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.carlosouzadev.libraryapi.controller.dto.RespostaPesquisaLivroDTO;
import com.github.carlosouzadev.libraryapi.controller.mapper.LivroMapper;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {

        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);

        URI uri = geralHeaderLocation(livro.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespostaPesquisaLivroDTO> buscar(@PathVariable UUID id) {

        Optional<Livro> opt = livroService.buscar(id);

        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Livro livro = opt.get();
        return ResponseEntity.ok(livroMapper.toDTO(livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        Optional<Livro> opt = livroService.buscar(id);

        opt.ifPresent(livroService::deletar);

        return ResponseEntity.noContent().build();
    }
}
