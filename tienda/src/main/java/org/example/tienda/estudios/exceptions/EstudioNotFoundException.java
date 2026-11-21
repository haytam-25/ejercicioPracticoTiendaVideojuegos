package org.example.tienda.estudios.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** YA ESTA HECHA. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EstudioNotFoundException extends RuntimeException {
    public EstudioNotFoundException(Long id) {
        super("No se ha encontrado el estudio con id: " + id);
    }
}
