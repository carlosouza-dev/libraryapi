package com.github.carlosouza_dev.libraryapi.controller.dto;

import com.github.carlosouza_dev.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.ISBN;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank
        String isbn,

        @NotBlank
        String titulo,

        @PastOrPresent
        @NotNull
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal valor,

        @NotNull
        UUID idAutor
) {
}
