package org.example.tienda.videojuegos.dto;

import java.math.BigDecimal;

/** YA ESTA HECHO. Lo que SALE: lleva el NOMBRE del estudio y no expone "deleted". */
public record VideojuegoResponse(
        Long id,
        String titulo,
        String codigo,
        BigDecimal precio,
        Integer unidades,
        Integer edadMinima,
        String estudio
) {}
