package com.github.carlosouzadev.libraryapi.service;

import com.github.carlosouzadev.libraryapi.model.GeneroLivro;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.repository.LivroRepository;
import com.github.carlosouzadev.libraryapi.repository.specs.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscar(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        livroRepository.delete(livro);
    }

    public List<Livro> filtrar(
            String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer ano
    ){
        Specification<Livro> specs = Specification
                .where(LivroSpecs.isbnEqual(isbn))
                .and(LivroSpecs.tituloLike(titulo))
                .and(LivroSpecs.nomeAutorEqual(nomeAutor))
                .and(LivroSpecs.generoEqual(genero))
                .and(LivroSpecs.anoPublicacaoEqual(ano));

        return livroRepository.findAll(specs);
    }
}
