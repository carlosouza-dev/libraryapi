package com.github.carlosouzadev.libraryapi.validator;

import com.github.carlosouzadev.libraryapi.exception.RegistroDuplicadoException;
import com.github.carlosouzadev.libraryapi.model.Autor;
import com.github.carlosouzadev.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if (jaExisteAutor(autor)){
            throw new RegistroDuplicadoException("Autor(a) já cadastrado");
        }
    }

    private boolean jaExisteAutor(Autor autor) {
        Optional<Autor> optAutor = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if (autor.getId() == null){
            return optAutor.isPresent();
        }

        return optAutor.isPresent() && !optAutor.get().getId().equals(autor.getId());
    }
}
