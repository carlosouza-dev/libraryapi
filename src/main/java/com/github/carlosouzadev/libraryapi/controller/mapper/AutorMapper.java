package com.github.carlosouzadev.libraryapi.controller.mapper;

import com.github.carlosouzadev.libraryapi.controller.dto.AutorDTO;
import com.github.carlosouzadev.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);
}
