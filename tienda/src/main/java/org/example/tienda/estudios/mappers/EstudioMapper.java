package org.example.tienda.estudios.mappers;

import org.example.tienda.estudios.dto.EstudioResponse;
import org.example.tienda.estudios.models.Estudio;
import org.springframework.stereotype.Component;

/** YA ESTA HECHO. */
@Component
public class EstudioMapper {

    public EstudioResponse toResponse(Estudio e, Long juegosDisponibles) {
        return new EstudioResponse(e.getId(), e.getNombre(), e.getPais(), juegosDisponibles);
    }
}
