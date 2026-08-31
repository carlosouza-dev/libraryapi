package com.github.carlosouzadev.libraryapi.repository.specs;

import com.github.carlosouzadev.libraryapi.model.GeneroLivro;
import com.github.carlosouzadev.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Locale;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root, query, cb) -> {
            if (isbn == null) return null;

            return cb.equal(root.get("isbn"), isbn);
        });
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) -> {
            if (titulo == null) return null;

            return cb.like(
                    cb.lower(root.get("titulo")),
                    "%" + titulo.toLowerCase(Locale.ROOT) + "%"
            );
        };
    }

    public static Specification<Livro> nomeAutorEqual(String nome) {
        return ((root, query, cb) -> {
            if (nome == null) return null;

//            Inner Join como padrão
//            return cb.like(cb.lower(root.get("autor").get("nome")),
//                    "%" + nome.toLowerCase(Locale.ROOT) + "%");

            // Define o Tipo de Join
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like(cb.lower(joinAutor.get("nome")),
                    "%" + nome.toLowerCase(Locale.ROOT) + "%");
        });
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return ((root, query, cb) -> {
            if (genero == null) return null;

            return cb.equal(root.get("genero"), genero);
        });
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer ano) {
        return ((root, query, cb) -> {
            if (ano == null) return null;

            LocalDateTime inicioAno = LocalDateTime
                    .of(ano, 1, 1, 0, 0, 0);
            LocalDateTime fimAno = LocalDateTime
                    .of(ano, 12, 31, 23, 59, 59);

            return cb.between(root.get("dataPublicacao"), inicioAno, fimAno);
        });
    }
}
