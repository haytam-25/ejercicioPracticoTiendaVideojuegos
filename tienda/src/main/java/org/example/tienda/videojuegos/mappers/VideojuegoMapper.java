package org.example.tienda.videojuegos.mappers;

import org.example.tienda.estudios.models.Estudio;
import org.example.tienda.videojuegos.dto.VideojuegoRequest;
import org.example.tienda.videojuegos.dto.VideojuegoResponse;
import org.example.tienda.videojuegos.models.Videojuego;
import org.springframework.stereotype.Component;

/**
 * YA ESTA HECHO. No lo toques, pero LEELO: el estudio tiene que llegar
 * ya buscado (como entidad) cuando llames a toEntity o a actualizar.
 */
@Component
public class VideojuegoMapper {

    /** Entidad -> DTO. Toca getEstudio(): llamalo DENTRO de la transaccion. */
    public VideojuegoResponse toResponse(Videojuego v) {
        return new VideojuegoResponse(
                v.getId(), v.getTitulo(), v.getCodigo(), v.getPrecio(),
                v.getUnidades(), v.getEdadMinima(), v.getEstudio().getNombre());
    }

    /** POST: un videojuego nuevo. */
    public Videojuego toEntity(VideojuegoRequest r, Estudio estudio) {
        Videojuego videojuego = new Videojuego();
        actualizar(videojuego, r, estudio);
        return videojuego;
    }

    /** PUT: copia TODOS los campos del request al videojuego. */
    public void actualizar(Videojuego videojuego, VideojuegoRequest r, Estudio estudio) {
        videojuego.setTitulo(r.titulo().trim());
        videojuego.setCodigo(r.codigo().trim());
        videojuego.setPrecio(r.precio());
        videojuego.setUnidades(r.unidades());
        videojuego.setEdadMinima(r.edadMinima());
        videojuego.setEstudio(estudio);
    }
}
