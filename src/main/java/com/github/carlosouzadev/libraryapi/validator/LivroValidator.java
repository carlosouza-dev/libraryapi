package com.github.carlosouzadev.libraryapi.validator;

import com.github.carlosouzadev.libraryapi.exception.RegistroDuplicadoException;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if (existeIsbnCadastrada(livro)) {
            throw new RegistroDuplicadoException("O ISBN '" + livro.getIsbn() + "' já foi cadastrado");
        }
    }

    private boolean existeIsbnCadastrada(Livro livro) {
        Optional<Livro> optLivro = repository.findByIsbn(livro.getIsbn());

        // Se não achou o ISBN no banco, ele definitivamente NÃO existe cadastrado
        if (optLivro.isEmpty()) {
            return false;
        }

        // Se achou, ele existe cadastrado APENAS SE pertencer a OUTRO livro (IDs diferentes)
        return !optLivro.get().getId().equals(livro.getId());
    }
}
