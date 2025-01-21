package com.aluracursos.foro_hub.domain.usuario.dto;

import com.aluracursos.foro_hub.domain.usuario.entidad.Usuario;

public record DatosListadoUsuario(
        Long id,
        String nombre,
        String email,
        Long perfiles
) {

    public DatosListadoUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getPerfiles());
    }
}
