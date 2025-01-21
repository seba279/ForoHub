package com.aluracursos.foro_hub.controller;

import com.aluracursos.foro_hub.domain.curso.dto.DatosActualizarCurso;
import com.aluracursos.foro_hub.domain.curso.dto.DatosListadoCurso;
import com.aluracursos.foro_hub.domain.curso.dto.DatosRegistroCurso;
import com.aluracursos.foro_hub.domain.curso.dto.DatosRespuestaCurso;
import com.aluracursos.foro_hub.domain.curso.entidad.Curso;
import com.aluracursos.foro_hub.domain.curso.repository.CursoRepository;
import com.aluracursos.foro_hub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosListadoTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRespuestaTopico;
import com.aluracursos.foro_hub.domain.topico.entidad.Topico;
import com.aluracursos.foro_hub.infra.error.ErrorResponse;
import com.aluracursos.foro_hub.infra.error.RecursoNoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaCurso> agregarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso, UriComponentsBuilder uriComponentsBuilder) {
        Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
        DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );

        URI url = uriComponentsBuilder.path("/cursos/{id}")
                .buildAndExpand(curso.getId())
                .toUri();
        return ResponseEntity.created(url).body(datosRespuestaCurso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoCurso>> listadoCursos(@PageableDefault(page = 0, size = 10, sort = "nombre") Pageable paginacion) {
        return ResponseEntity.ok(cursoRepository.findAll(paginacion).map(DatosListadoCurso::new));
    }

    @GetMapping("/categoria")
    public ResponseEntity<Page<DatosListadoCurso>> listadoCursosCategoria(@PageableDefault(page = 0, size = 10) Pageable paginacion) {
        return ResponseEntity.ok(cursoRepository.findAllOrderByCategoria(paginacion).map(DatosListadoCurso::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
        // Verificamos si el ID existe en la base de datos
        Optional<Curso> buscarCurso = cursoRepository.findById(datosActualizarCurso.id());

        if (buscarCurso.isPresent()) {
            Curso curso = buscarCurso.get();
            curso.actualizarDatos(datosActualizarCurso);

            return ResponseEntity.ok(new DatosRespuestaCurso(
                    curso.getId(),
                    curso.getNombre(),
                    curso.getCategoria()
            ));
        } else {
            throw new RecursoNoEncontradoException("El id "+ datosActualizarCurso.id() + " ingresado del curso no existe");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> retornaDatosCurso(@PathVariable Long id) {

        if(!cursoRepository.existsById(id)){
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado del usuario no existe");
        }

        Curso curso = cursoRepository.getReferenceById(id);
        var datosCurso = new DatosRespuestaCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
        return ResponseEntity.ok(datosCurso);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarCurso(@PathVariable Long id) {
        Optional<Curso> buscarCurso = cursoRepository.findById(id);
        if (buscarCurso.isPresent()) {
            cursoRepository.deleteById(id);
        }else {
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado del curso no existe");
        }
        return ResponseEntity.noContent().build();
    }
}
