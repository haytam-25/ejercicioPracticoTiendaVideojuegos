package org.example.tienda.videojuegos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** YA ESTA HECHA. 400: los datos no sirven (el estudio no existe). */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VideojuegoBadRequestException extends RuntimeException {
    public VideojuegoBadRequestException(String mensaje) { super(mensaje); }
}
