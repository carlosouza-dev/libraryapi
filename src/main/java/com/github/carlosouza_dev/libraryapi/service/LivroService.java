package com.github.carlosouza_dev.libraryapi.service;

import com.github.carlosouza_dev.libraryapi.repository.LivroRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
}
