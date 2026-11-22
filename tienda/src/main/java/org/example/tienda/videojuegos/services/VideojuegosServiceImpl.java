package org.example.tienda.videojuegos.services;

import org.example.tienda.estudios.exceptions.EstudioNotFoundException;
import org.example.tienda.estudios.repositories.EstudiosRepository;
import org.example.tienda.videojuegos.dto.VideojuegoRequest;
import org.example.tienda.videojuegos.dto.VideojuegoResponse;
import org.example.tienda.videojuegos.exceptions.VideojuegoBadRequestException;
import org.example.tienda.videojuegos.exceptions.VideojuegoConflictException;
import org.example.tienda.videojuegos.exceptions.VideojuegoNotFoundException;
import org.example.tienda.videojuegos.exceptions.VideojuegoSinUnidadesException;
import org.example.tienda.videojuegos.mappers.VideojuegoMapper;
import org.example.tienda.videojuegos.models.Videojuego;
import org.example.tienda.videojuegos.repositories.VideojuegosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * TAREA 5 · Sustituye cada throw por la implementacion.
 *   5.0 · Inyecta tambien el VideojuegosRepository por el constructor.
 * Si un metodo no te sale, DEJA EL THROW y sigue: el proyecto tiene que compilar.
 */
@Service
public class VideojuegosServiceImpl implements VideojuegosService {

    private final EstudiosRepository estudiosRepository;
    private final VideojuegosRepository videojuegosRepository;
    private final VideojuegoMapper mapper;

    public VideojuegosServiceImpl(EstudiosRepository estudiosRepository, VideojuegosRepository videojuegosRepository,
                                  VideojuegoMapper mapper) {
        this.estudiosRepository = estudiosRepository;
        this.videojuegosRepository = videojuegosRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VideojuegoResponse> findAll(Optional<String> titulo,
                                            Optional<String> estudio,
                                            Optional<Integer> edadMax,
                                            Pageable pageable)
    {
        Specification<Videojuego> spec = (root, query, cb) ->
                cb.isFalse(root.get("deleted"));

        if(titulo.isPresent()){
            spec= spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("titulo")), "%" + titulo.get().toLowerCase()+ "%"));
        }

        if (estudio.isPresent()){
            spec=spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("estudio")), "%" + estudio.get().toLowerCase()+"%"));
        }
        if (edadMax.isPresent()){
            spec= spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("edadMinima"),edadMax.get()));
        }

        return videojuegosRepository.findAll(spec,pageable).map(mapper::toResponse);

    }

    @Transactional(readOnly = true)
    @Override
    public VideojuegoResponse findById(Long id) {

        return videojuegosRepository.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new VideojuegoNotFoundException(id));

    }

    @Transactional
    @Override
    public VideojuegoResponse save(VideojuegoRequest request) {

        if(videojuegosRepository.existsByCodigo(request.codigo())){
            throw new VideojuegoBadRequestException("Ya existe un videojuego con el codigo: "+request.codigo());
        }

        var estudio = estudiosRepository.findById(request.estudioId())
                .orElseThrow(() -> new EstudioNotFoundException(request.estudioId()));

        var videojuegomodelado = mapper.toEntity(request,estudio);

        var videojuegoGuardado = videojuegosRepository.save(videojuegomodelado);

        return mapper.toResponse(videojuegoGuardado);
    }

    @Transactional
    @Override
    public VideojuegoResponse update(Long id, VideojuegoRequest request) {

        var videojuego= videojuegosRepository.findById(id)
                .orElseThrow(() -> new VideojuegoNotFoundException(id));

        if(!videojuego.getCodigo().equals(request.codigo())){
            if (videojuegosRepository.existsByCodigo(request.codigo())){
                throw new VideojuegoConflictException("Ya existe un videojuego con el código "+request.codigo());
            }
        }
        var estudio = estudiosRepository.findById(request.estudioId())
                .orElseThrow(() -> new EstudioNotFoundException(request.estudioId()));

        mapper.actualizar(videojuego,request,estudio);

        var videojuegoact = videojuegosRepository.save(videojuego);

        return mapper.toResponse(videojuegoact);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        var videojuego = videojuegosRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new VideojuegoNotFoundException(id));

        videojuego.setDeleted(true);
        videojuegosRepository.save(videojuego);

    }

    @Transactional
    @Override
    public VideojuegoResponse ajustarUnidades(Long id, int cantidad) {

        var videojuego = videojuegosRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new VideojuegoNotFoundException(id));
        var nuevasunidades = videojuego.getUnidades()+cantidad;

        if (nuevasunidades<0){
            throw new VideojuegoSinUnidadesException(videojuego.getTitulo(),videojuego.getUnidades(),cantidad);
        }

        videojuego.setUnidades(nuevasunidades);
        var videoAjustado= videojuegosRepository.save(videojuego);

        return mapper.toResponse(videoAjustado);
    }
}
