package com.github.carlosouzadev.libraryapi.validator;

import com.github.carlosouzadev.libraryapi.exception.CampoInvalidoException;
import com.github.carlosouzadev.libraryapi.exception.RegistroDuplicadoException;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final static int ANO_EXIGENCIA_PRECO = 2020;
    private final LivroRepository repository;

    public void validar(Livro livro) {
        if (existeIsbnCadastrada(livro)) {
            throw new RegistroDuplicadoException("O ISBN '" + livro.getIsbn() + "' já foi cadastrado");
        }

        if (isPrecoLivroObrigatorio(livro) && livro.getPreco() == null){
            throw new CampoInvalidoException(
                    "preco",
                    "Para livros publicados a partir de " + ANO_EXIGENCIA_PRECO + ", o preço é obrigatório");
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

    private boolean isPrecoLivroObrigatorio(Livro livro) {
        return livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }
}
