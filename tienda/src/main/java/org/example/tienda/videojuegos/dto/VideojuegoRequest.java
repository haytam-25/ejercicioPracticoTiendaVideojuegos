package org.example.tienda.videojuegos.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * TAREA 3.a · Lo que ENTRA en el POST y en el PUT.
 * Los campos ya estan (el mapper los usa). Añade las validaciones
 * de la tabla del enunciado, con mensajes propios.
 */
public record VideojuegoRequest(

        @NotBlank(message = "Titulo no puede estar vacio")
        @Size(min = 1, max = 150 ,message = "Minimo 1 caracter y maximo 150")
        @NotNull(message = "Titulo obligatorio")
        String titulo,

        @NotNull(message = "Codigo obligatorio")
        @NotBlank(message = "Codigo no puede estar vacio")
        @Pattern(regexp = "[A-Z]{3}-\\d{4}-[A-Z]", message = "Formato del codigo no valido")
        String codigo,

        @NotNull(message = "Precio obligatorio")
        @PositiveOrZero(message = "Precio no puede ser negativo")
        BigDecimal precio,

        @NotNull(message = "Unidades obligatorias")
        @Min(value = 0, message = "Unidades tiene que ser entre 0-999")
        @Max(value = 999, message = "Unidades tiene que ser entre 0-999")
        Integer unidades,

        @NotNull(message = "Edad minima tiene que ser obligatoria")
        @Min(value = 3, message = "Edad tiene que ser entre 3 y 18")
        @Max(value = 18, message = "Edad tiene que ser entre 3 y 18")
        Integer edadMinima,

        @NotNull(message = "estudioId obligatorio")
        Long estudioId
) {}
