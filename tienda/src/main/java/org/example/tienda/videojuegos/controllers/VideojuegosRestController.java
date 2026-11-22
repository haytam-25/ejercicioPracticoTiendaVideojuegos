package org.example.tienda.videojuegos.controllers;

import jakarta.validation.Valid;
import org.example.tienda.videojuegos.dto.UnidadesRequest;
import org.example.tienda.videojuegos.dto.VideojuegoRequest;
import org.example.tienda.videojuegos.dto.VideojuegoResponse;
import org.example.tienda.videojuegos.models.Videojuego;
import org.example.tienda.videojuegos.services.VideojuegosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

/**
 * TAREA 6 · Los seis endpoints de la tabla del enunciado.
 * Fijate en EstudiosRestController: es el mismo patron.
 */
@RestController
@RequestMapping("${api.path}/${api.version}/videojuegos")
public class VideojuegosRestController {

    private final VideojuegosService servicio;

    public VideojuegosRestController(VideojuegosService servicio) {
        this.servicio = servicio;
    }

    // TODO 6 · los seis endpoints de la tabla de la TAREA 6 del enunciado

    /** YA ESTA HECHO. Para comprobar que todo arranca: GET /api/v1/videojuegos/ping */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("La API de videojuegos responde");
    }

    /**
     * YA ESTA HECHO. Llamalo en el GET del listado: solo deja ordenar por
     * id, titulo, precio, unidades y edadMinima, y el tamaño no pasa de 50.
     */
    private Pageable sanear(Pageable p) {
        var permitidos = Set.of("id", "titulo", "precio", "unidades", "edadMinima");
        var orden = p.getSort().stream()
                .filter(o -> permitidos.contains(o.getProperty()))
                .toList();
        return PageRequest.of(p.getPageNumber(), Math.min(p.getPageSize(), 50),
                orden.isEmpty() ? Sort.by("titulo") : Sort.by(orden).and(Sort.by("id")));
    }

    @GetMapping
    public ResponseEntity<Page<VideojuegoResponse>> findAll (
            @RequestParam Optional<String> titulo,
            @RequestParam Optional<String> estudio,
            @RequestParam Optional<Integer> edadMax,
            @PageableDefault(size = 10,sort = "titulo") Pageable pageable
            )
    {
        return ResponseEntity.ok(servicio.findAll(titulo, estudio, edadMax,sanear(pageable)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoResponse> findById (@PathVariable Long id){
        return ResponseEntity.ok(servicio.findById(id));
    }

    @PostMapping
    public ResponseEntity<VideojuegoResponse> save (@Valid @RequestBody VideojuegoRequest videojuegoRequest){
        var creado = servicio.save(videojuegoRequest);

        return ResponseEntity.created(URI.create("/api/v1/videojuegos/"+creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideojuegoResponse> update (@Valid @RequestBody VideojuegoRequest videojuegoRequest, @PathVariable Long id ){
        var actualizado = servicio.update(id,videojuegoRequest);

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VideojuegoResponse> deleteById (@PathVariable Long id ){
        servicio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/unidades")
    public ResponseEntity<VideojuegoResponse> ajustarUnidades (@PathVariable Long id, @Valid @RequestBody UnidadesRequest unidadesRequest){
        var ajustado = servicio.ajustarUnidades(id,unidadesRequest.cantidad());
        return ResponseEntity.ok(ajustado);
    }

}
