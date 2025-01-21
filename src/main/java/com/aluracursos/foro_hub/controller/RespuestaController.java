package com.aluracursos.foro_hub.controller;

import com.aluracursos.foro_hub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.aluracursos.foro_hub.domain.respuesta.dto.DatosListadoRespuesta;
import com.aluracursos.foro_hub.domain.respuesta.dto.DatosRegistroRespuesta;
import com.aluracursos.foro_hub.domain.respuesta.dto.DatosRespuesta;
import com.aluracursos.foro_hub.domain.respuesta.entidad.Respuesta;
import com.aluracursos.foro_hub.domain.respuesta.repository.RespuestaRepository;
import com.aluracursos.foro_hub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosListadoTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRegistroTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRespuestaTopico;
import com.aluracursos.foro_hub.domain.topico.entidad.Topico;
import com.aluracursos.foro_hub.domain.topico.repository.TopicoRepository;
import com.aluracursos.foro_hub.domain.usuario.repository.UsuarioRepository;
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
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuesta> agregarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta, UriComponentsBuilder uriComponentsBuilder) {

        if(!topicoRepository.existsById(datosRegistroRespuesta.topico())){
            throw new RecursoNoEncontradoException("El id " + datosRegistroRespuesta.topico() +" ingresado del topico no existe");
        }

        if(!usuarioRepository.existsById(datosRegistroRespuesta.autor())){
            throw new RecursoNoEncontradoException("El id " + datosRegistroRespuesta.autor() +" ingresado del usuario no existe");
        }

        Respuesta respuesta = respuestaRepository.save(new Respuesta(datosRegistroRespuesta));
        DatosRespuesta datosRespuesta = new DatosRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico(),
                respuesta.getFecha_creacion(),
                respuesta.getAutor(),
                respuesta.getSolucion()
        );

        URI url = uriComponentsBuilder.path("/respuestas/{id}")
                .buildAndExpand(respuesta.getId())
                .toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }


    //Listar Respuestas
    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listadoRespuestas(@PageableDefault(page = 0, size = 10) Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findAll(paginacion).map(DatosListadoRespuesta::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        // Verificamos si el ID existe en la base de datos
        Optional<Respuesta> buscarRespuesta = respuestaRepository.findById(datosActualizarRespuesta.id());

        if (buscarRespuesta.isPresent()) {
            Respuesta respuesta = buscarRespuesta.get();
            respuesta.actualizarDatos(datosActualizarRespuesta);

            return ResponseEntity.ok(new DatosRespuesta(
                    respuesta.getId(),
                    respuesta.getMensaje(),
                    respuesta.getTopico(),
                    respuesta.getFecha_creacion(),
                    respuesta.getAutor(),
                    respuesta.getSolucion()
            ));
        } else {
            throw new RecursoNoEncontradoException("El id "+ datosActualizarRespuesta.id() + " ingresado de la respuesta no existe");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta(@PathVariable Long id) {
        Optional<Respuesta> buscarRespuesta = respuestaRepository.findById(id);
        if (buscarRespuesta.isPresent()) {
            respuestaRepository.deleteById(id);
        }else {
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado de la respuesta no existe");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuesta> retornaDatosRespuesta(@PathVariable Long id) {

        if(!respuestaRepository.existsById(id)){
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado de la respuesta no existe");
        }

        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        var datosRespuesta = new DatosRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico(),
                respuesta.getFecha_creacion(),
                respuesta.getAutor(),
                respuesta.getSolucion()
        );
        return ResponseEntity.ok(datosRespuesta);
    }
}
