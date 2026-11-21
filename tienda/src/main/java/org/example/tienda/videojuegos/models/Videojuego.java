package org.example.tienda.videojuegos.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tienda.estudios.models.Estudio;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TAREA 1 · Convierte esta clase en una ENTIDAD JPA que genere exactamente
 * la tabla "videojuegos" de la TAREA 1 del enunciado.
 *
 * Los campos, los getters/setters (Lombok) y el constructor vacio ya estan:
 * NO cambies sus nombres ni sus tipos, porque el mapper los usa.
 * Todavia NO es una entidad: por eso el proyecto arranca sin ella.
 */
@Getter
@Setter
@Entity
@Table(name = "videojuegos")
@NoArgsConstructor
public class Videojuego {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "codigo", length = 12, nullable = false, unique = true)
    private String codigo;

    @Column(name = "precio",precision = 8,scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "unidades",nullable = false)
    private Integer unidades;

    @Column(name = "edad_minima", nullable = false)
    private Integer edadMinima;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudio_id", nullable = false)
    private Estudio estudio;

    @Column(name = "is_deleted",nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();
}
