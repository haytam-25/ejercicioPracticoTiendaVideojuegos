package org.example.tienda.videojuegos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UnidadesRequest(
        @NotNull(message = "Cantidad obligatoria")
        @Min(value = -20, message = "Cantidad tiene que ser entre -20 y 20")
        @Max(value = 20, message = "Cantidad tiene que ser entre -20 y 20")
        Integer cantidad
)
{}
