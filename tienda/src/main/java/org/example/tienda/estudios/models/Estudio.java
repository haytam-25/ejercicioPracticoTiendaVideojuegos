package org.example.tienda.estudios.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tienda.videojuegos.models.Videojuego;

import java.util.ArrayList;
import java.util.List;

/**
 * YA ESTA HECHA, salvo el lado inverso de la relacion (TAREA 1.e).
 * Fijate en ella para hacer Videojuego.
 */
@Entity
@Table(name = "estudios")
@Getter
@Setter
@NoArgsConstructor
public class Estudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 120)
    private String nombre;

    @Column(nullable = false, length = 60)
    private String pais;

    @OneToMany(mappedBy = "estudio")
    private List<Videojuego> juegos = new ArrayList<>();


    // TODO 1.e · el lado INVERSO de la relacion con Videojuego
}
