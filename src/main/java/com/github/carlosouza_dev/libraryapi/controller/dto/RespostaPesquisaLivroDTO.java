package com.github.carlosouza_dev.libraryapi.controller.dto;

import com.github.carlosouza_dev.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RespostaPesquisaLivroDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro genero,
        BigDecimal valor,
        AutorDTO autor
) {
}
