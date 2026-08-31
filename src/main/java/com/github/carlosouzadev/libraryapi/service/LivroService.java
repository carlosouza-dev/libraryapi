package com.github.carlosouzadev.libraryapi.service;

import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
