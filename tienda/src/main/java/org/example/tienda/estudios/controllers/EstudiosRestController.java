package org.example.tienda.estudios.controllers;

import org.example.tienda.estudios.dto.EstudioResponse;
import org.example.tienda.estudios.services.EstudiosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** EJEMPLO RESUELTO: tu VideojuegosRestController sigue este patron. */
@RestController
@RequestMapping("${api.path}/${api.version}/estudios")
public class EstudiosRestController {

    private final EstudiosService servicio;

    public EstudiosRestController(EstudiosService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<Page<EstudioResponse>> findAll(
            @PageableDefault(size = 10, sort = "nombre") Pageable pageable) {
        return ResponseEntity.ok(servicio.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudioResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.findById(id));
    }
}
