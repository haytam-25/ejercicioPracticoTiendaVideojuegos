package org.example.tienda.videojuegos.services;

import org.example.tienda.estudios.repositories.EstudiosRepository;
import org.example.tienda.videojuegos.dto.VideojuegoRequest;
import org.example.tienda.videojuegos.dto.VideojuegoResponse;
import org.example.tienda.videojuegos.mappers.VideojuegoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TAREA 5 · Sustituye cada throw por la implementacion.
 *   5.0 · Inyecta tambien el VideojuegosRepository por el constructor.
 * Si un metodo no te sale, DEJA EL THROW y sigue: el proyecto tiene que compilar.
 */
@Service
public class VideojuegosServiceImpl implements VideojuegosService {

    private final EstudiosRepository estudiosRepository;
    private final VideojuegoMapper mapper;

    public VideojuegosServiceImpl(EstudiosRepository estudiosRepository,
                                  VideojuegoMapper mapper) {
        this.estudiosRepository = estudiosRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<VideojuegoResponse> findAll(Optional<String> titulo,
                                            Optional<String> estudio,
                                            Optional<Integer> edadMax,
                                            Pageable pageable) {
        throw new UnsupportedOperationException("TODO 5.b");
    }

    @Override
    public VideojuegoResponse findById(Long id) {
        throw new UnsupportedOperationException("TODO 5.a");
    }

    @Override
    public VideojuegoResponse save(VideojuegoRequest request) {
        throw new UnsupportedOperationException("TODO 5.a");
    }

    @Override
    public VideojuegoResponse update(Long id, VideojuegoRequest request) {
        throw new UnsupportedOperationException("TODO 5.a");
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("TODO 5.a");
    }

    @Override
    public VideojuegoResponse ajustarUnidades(Long id, int cantidad) {
        throw new UnsupportedOperationException("TODO 5.c");
    }
}
