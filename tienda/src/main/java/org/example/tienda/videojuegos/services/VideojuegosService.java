package org.example.tienda.videojuegos.services;

import org.example.tienda.videojuegos.dto.VideojuegoRequest;
import org.example.tienda.videojuegos.dto.VideojuegoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/** YA ESTA HECHA: es el CONTRATO. No cambies las firmas. */
public interface VideojuegosService {

    /** Listado paginado con tres filtros OPCIONALES y acumulables. */
    Page<VideojuegoResponse> findAll(Optional<String> titulo,
                                     Optional<String> estudio,
                                     Optional<Integer> edadMax,
                                     Pageable pageable);

    VideojuegoResponse findById(Long id);

    VideojuegoResponse save(VideojuegoRequest request);

    /** PUT: reemplaza todos los campos. */
    VideojuegoResponse update(Long id, VideojuegoRequest request);

    /** Borrado LOGICO. */
    void deleteById(Long id);

    /** Suma (cantidad positiva) o resta (negativa) unidades. */
    VideojuegoResponse ajustarUnidades(Long id, int cantidad);
}
