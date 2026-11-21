package org.example.tienda.videojuegos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VideojuegoSinUnidadesException extends RuntimeException {
    public VideojuegoSinUnidadesException(String titulo, int unidades, int cantidad) {
        super("No quedan unidades suficientes de "+titulo+": hay "+unidades+" y se piden "+cantidad);
    }
}
