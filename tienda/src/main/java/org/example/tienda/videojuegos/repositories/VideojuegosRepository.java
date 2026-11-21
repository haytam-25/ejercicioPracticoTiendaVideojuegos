package org.example.tienda.videojuegos.repositories;

import org.example.tienda.videojuegos.models.Videojuego;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * TAREA 2 · El repositorio de videojuegos. Ahora es una interfaz vacia:
 * todavia no es un repositorio de Spring Data.
 * Lo que tiene que tener esta en el enunciado (TAREA 2).
 */
public interface VideojuegosRepository extends JpaRepository<Videojuego, Long> , JpaSpecificationExecutor<Videojuego> {

    Optional<Videojuego> findByIdAndDeletedFalse(Long id);

    boolean existsByCodigo(String codigo);

}
