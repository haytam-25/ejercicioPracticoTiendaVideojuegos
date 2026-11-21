package org.example.tienda.estudios.dto;

/**
 * YA ESTA HECHO. juegosDisponibles: los videojuegos NO borrados del estudio
 * que tienen al menos 1 unidad. Solo se rellena en GET /estudios/{id};
 * en el listado va a null.
 */
public record EstudioResponse(Long id, String nombre, String pais, Long juegosDisponibles) {}
