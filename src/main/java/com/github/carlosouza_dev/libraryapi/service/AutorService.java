package com.github.carlosouza_dev.libraryapi.service;

import com.github.carlosouza_dev.libraryapi.exception.OperacaoNaoPermitidaException;
import com.github.carlosouza_dev.libraryapi.model.Autor;
import com.github.carlosouza_dev.libraryapi.repository.AutorRepository;
import com.github.carlosouza_dev.libraryapi.repository.LivroRepository;
import com.github.carlosouza_dev.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if (autor.getId() == null) throw new IllegalArgumentException("Autor precisa ter id para atualizar");

        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> buscar(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if (possuiLivros(autor))
            throw new OperacaoNaoPermitidaException("Não é possível excluir Autor(a) que possua livros cadastrados");
        repository.delete(autor);
    }

    private boolean possuiLivros(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

    public List<Autor> filtrar(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null) {
            return repository.findByNome(nome);
        }

        if (nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }
}
