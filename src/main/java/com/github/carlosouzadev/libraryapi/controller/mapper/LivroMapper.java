package com.github.carlosouzadev.libraryapi.controller.mapper;

import com.github.carlosouzadev.libraryapi.controller.dto.CadastroLivroDTO;
import com.github.carlosouzadev.libraryapi.controller.dto.RespostaPesquisaLivroDTO;
import com.github.carlosouzadev.libraryapi.model.Livro;
import com.github.carlosouzadev.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {
    @Autowired
    AutorRepository repository;

    @Mapping(target = "autor", expression = "java( repository.findById(dto.idAutor()).orElse(null) )")
    @Mapping(target = "preco", source = "valor")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(target = "valor", source = "preco")
    public abstract RespostaPesquisaLivroDTO toDTO(Livro livro);
}
