package com.github.carlosouzadev.libraryapi.controller.dto;

import com.github.carlosouzadev.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "nome é um campo obrigatório")
        String nome,
        @NotNull(message = "dataNascimento é um campo obrigatório")
        LocalDate dataNascimento,
        @NotBlank(message = "nacionalidade é um campo obrigatório")
        String nacionalidade
){}
