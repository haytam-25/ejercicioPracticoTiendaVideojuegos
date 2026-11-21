package org.example.tienda.videojuegos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** YA ESTA HECHA. 409: choca con otro videojuego (codigo repetido). */
@ResponseStatus(HttpStatus.CONFLICT)
public class VideojuegoConflictException extends RuntimeException {
    public VideojuegoConflictException(String mensaje) { super(mensaje); }
}
