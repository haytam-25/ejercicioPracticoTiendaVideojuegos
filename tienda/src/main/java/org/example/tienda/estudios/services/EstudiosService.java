package org.example.tienda.estudios.services;

import org.example.tienda.estudios.dto.EstudioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** YA ESTA HECHO. */
public interface EstudiosService {
    Page<EstudioResponse> findAll(Pageable pageable);
    EstudioResponse findById(Long id);
}
