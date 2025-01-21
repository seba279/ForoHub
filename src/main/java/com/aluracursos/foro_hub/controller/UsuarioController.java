package com.aluracursos.foro_hub.controller;


import com.aluracursos.foro_hub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosListadoTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRegistroTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRespuestaTopico;
import com.aluracursos.foro_hub.domain.topico.entidad.Topico;
import com.aluracursos.foro_hub.domain.usuario.dto.DatosActualizarUsuario;
import com.aluracursos.foro_hub.domain.usuario.dto.DatosListadoUsuario;
import com.aluracursos.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import com.aluracursos.foro_hub.domain.usuario.dto.DatosRespuestaUsuario;
import com.aluracursos.foro_hub.domain.usuario.entidad.Usuario;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> agregarUsuario(@RequestBody @Valid DatosRegistroUsuario datosRegistroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
        DatosRespuestaUsuario datosRespuestaUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPerfiles()
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();
        return ResponseEntity.created(url).body(datosRespuestaUsuario);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> listadoUsuarios(@PageableDefault(page = 0, size = 10) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosListadoUsuario::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> retornaDatosUsuario(@PathVariable Long id) {

        if(!usuarioRepository.existsById(id)){
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado del usuario no existe");
        }

        Usuario usuario = usuarioRepository.getReferenceById(id);
        var datosUsuario = new DatosRespuestaUsuario(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPerfiles()
        );
        return ResponseEntity.ok(datosUsuario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {

        Optional<Usuario> buscarUsuario = usuarioRepository.findById(id);

        if (buscarUsuario.isPresent()) {
            Usuario usuario = buscarUsuario.get();
            usuarioRepository.deleteById(id);
        }else {
            throw new RecursoNoEncontradoException("El id "+ id + " ingresado del usuario no existe");
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizarUsuario(@RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario) {
        // Verificamos si el ID existe en la base de datos
        Optional<Usuario> buscarUsuario = usuarioRepository.findById(datosActualizarUsuario.id());

        if (buscarUsuario.isPresent()) {
            Usuario usuario = buscarUsuario.get();
            usuario.actualizarDatos(datosActualizarUsuario);

            return ResponseEntity.ok(new DatosRespuestaUsuario(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getPerfiles()
            ));
        } else {
            throw new RecursoNoEncontradoException("El id "+ datosActualizarUsuario.id() + " ingresado del usuario no existe");
        }
    }

}
