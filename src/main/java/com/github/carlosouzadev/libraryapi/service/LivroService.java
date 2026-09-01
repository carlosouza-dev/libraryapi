package com.github.carlosouzadev.libraryapi.service;

import com.github.carlosouzadev.libraryapi.model.GeneroLivro;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.repository.LivroRepository;
import com.github.carlosouzadev.libraryapi.repository.specs.LivroSpecs;
import com.github.carlosouzadev.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro) {
        livroValidator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscar(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public Page<Livro> filtrar(
            String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer ano,
            Integer numeroPagina, Integer tamanhoPagina, String campoOrdenacao
    ){
        Specification<Livro> specs = Specification
                .where(LivroSpecs.isbnEqual(isbn))
                .and(LivroSpecs.tituloLike(titulo))
                .and(LivroSpecs.nomeAutorEqual(nomeAutor))
                .and(LivroSpecs.generoEqual(genero))
                .and(LivroSpecs.anoPublicacaoEqual(ano));

        Pageable page = PageRequest.of(numeroPagina, tamanhoPagina, Sort.by(campoOrdenacao).descending());

        return livroRepository.findAll(specs, page);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) throw new IllegalArgumentException("Livro precisa ter um id para atualizar");
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
