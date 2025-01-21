package com.aluracursos.foro_hub.controller;

import com.aluracursos.foro_hub.domain.curso.repository.CursoRepository;
import com.aluracursos.foro_hub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRespuestaTopico;
import com.aluracursos.foro_hub.domain.topico.entidad.Topico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosListadoTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRegistroTopico;
import com.aluracursos.foro_hub.domain.topico.repository.TopicoRepository;
import com.aluracursos.foro_hub.domain.usuario.repository.UsuarioRepository;
import com.aluracursos.foro_hub.infra.error.ErrorResponse;
import com.aluracursos.foro_hub.infra.error.RecursoNoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> agregarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {

        if(!usuarioRepository.existsById(datosRegistroTopico.autor())){
            throw new RecursoNoEncontradoException("El id " + datosRegistroTopico.autor() +" ingresado del usuario no existe");
        }

        if(!cursoRepository.existsById(datosRegistroTopico.curso())){
            throw new RecursoNoEncontradoException("El id "+ datosRegistroTopico.curso() + " ingresado del curso no existe");
        }

        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso()
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    //Listar Topicos Activos
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(page = 0, size = 10)Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(paginacion).map(DatosListadoTopico::new));
    }

    //Listar Cursos en forma Asc y activos
    @GetMapping("/listarCursos")
    public ResponseEntity<Page<DatosListadoTopico>> listadoCursoTopico(@PageableDefault(page = 0, size = 10, sort = "curso") Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByCursoAsc(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/listarPorFecha")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicosPorFecha(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAllOrderByFechaCreacion(paginacion).map(DatosListadoTopico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        // Verificamos si el ID existe en la base de datos
        Optional<Topico> buscarTopico = topicoRepository.findById(datosActualizarTopico.id());

        if (buscarTopico.isPresent()) {
            Topico topico = buscarTopico.get();
            topico.actualizarDatos(datosActualizarTopico);

            return ResponseEntity.ok(new DatosRespuestaTopico(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFecha_creacion(),
                    topico.getEstado(),
                    topico.getAutor(),
                    topico.getCurso()
            ));
        } else {
            throw new RecursoNoEncontradoException("El id "+ datosActualizarTopico.id() + " ingresado del topico no existe");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> buscarTopico = topicoRepository.findById(id);
        if (buscarTopico.isPresent()) {
            Topico topico = buscarTopico.get();
            topico.desactivarTopico();
        }else {
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado del topico no existe");
        }
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id) {

        if(!topicoRepository.existsById(id)){
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado del topico no existe");
        }

        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso()
        );
        return ResponseEntity.ok(datosTopico);
    }
}
