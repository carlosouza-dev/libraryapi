package com.github.carlosouzadev.libraryapi.controller;

import com.github.carlosouzadev.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.carlosouzadev.libraryapi.controller.dto.RespostaPesquisaLivroDTO;
import com.github.carlosouzadev.libraryapi.controller.mapper.LivroMapper;
import com.github.carlosouzadev.libraryapi.model.GeneroLivro;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<Page<RespostaPesquisaLivroDTO>> filtrar(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String titulo,
            @RequestParam(name = "nome-autor", required = false) String nomeAutor,
            @RequestParam(name = "genero-livro", required = false) GeneroLivro generoLivro,
            @RequestParam(name = "ano-publicacao", required = false) Integer anoPublicacao,
            @RequestParam(name = "numero-pagina", defaultValue = "0") Integer numeroPagina,
            @RequestParam(name = "tamanho-pagina", defaultValue = "10") Integer tamanhoPagina
    ){
        Page<RespostaPesquisaLivroDTO> page = livroService.filtrar(
                isbn,
                titulo,
                nomeAutor,
                generoLivro,
                anoPublicacao,
                numeroPagina,
                tamanhoPagina
        ).map(livroMapper::toDTO);

        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid CadastroLivroDTO dto
    ){
        return livroService.buscar(id)
                .map(livro -> {
                    atualizarCamposLivro(livro, dto);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    private void atualizarCamposLivro(Livro livro, CadastroLivroDTO dto) {
        // faz a conversão de idAutor para autor automaticamente
        Livro aux = livroMapper.toEntity(dto);

        livro.setIsbn(aux.getIsbn());
        livro.setTitulo(aux.getTitulo());
        livro.setDataPublicacao(aux.getDataPublicacao());
        livro.setGenero(aux.getGenero());
        livro.setPreco(aux.getPreco());
        livro.setAutor(aux.getAutor());

        livroService.atualizar(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id){
        Optional<Livro> opt = livroService.buscar(id);

        opt.ifPresent(livroService::deletar);

        return ResponseEntity.noContent().build();
    }
}
