package org.example.tienda.videojuegos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** YA ESTA HECHA. 404: el videojuego no existe o esta borrado. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VideojuegoNotFoundException extends RuntimeException {
    public VideojuegoNotFoundException(Long id) {
        super("No se ha encontrado el videojuego con id: " + id);
    }
}
