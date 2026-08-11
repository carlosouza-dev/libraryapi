package com.github.carlosouza_dev.libraryapi.service;

import com.github.carlosouza_dev.libraryapi.model.Autor;
import com.github.carlosouza_dev.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if (autor.getId() == null) throw new IllegalArgumentException("Autor precisa ter id para atualizar");

        repository.save(autor);
    }

    public Optional<Autor> buscar(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        repository.delete(autor);
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
