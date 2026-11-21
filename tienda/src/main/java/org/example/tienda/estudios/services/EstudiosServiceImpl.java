package org.example.tienda.estudios.services;

import org.example.tienda.estudios.dto.EstudioResponse;
import org.example.tienda.estudios.exceptions.EstudioNotFoundException;
import org.example.tienda.estudios.mappers.EstudioMapper;
import org.example.tienda.estudios.repositories.EstudiosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** HECHO, salvo la TAREA 1.f (en findById). Es el patron de tu VideojuegosServiceImpl. */
@Service
public class EstudiosServiceImpl implements EstudiosService {

    private final EstudiosRepository repositorio;
    private final EstudioMapper mapper;

    public EstudiosServiceImpl(EstudiosRepository repositorio, EstudioMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    /** En el listado no se cuentan los juegos: juegosDisponibles va a null. */
    @Override
    @Transactional(readOnly = true)
    public Page<EstudioResponse> findAll(Pageable pageable) {
        return repositorio.findAll(pageable).map(e -> mapper.toResponse(e, null));
    }

    @Override
    @Transactional(readOnly = true)
    public EstudioResponse findById(Long id) {
        var estudio = repositorio.findById(id)
                .orElseThrow(() -> new EstudioNotFoundException(id));

        var juegosdisponibles = estudio.getJuegos().stream()
                .filter(videojuego -> !videojuego.isDeleted() && videojuego.getUnidades()>0).count();


        // TODO 1.f · juegosDisponibles = cuantos videojuegos NO borrados y con
        //            al menos 1 unidad tiene el estudio, contados con el lado
        //            inverso de la relacion (estudio.getJuegos())
        return mapper.toResponse(estudio, null);
    }
}
