package com.aluracursos.foro_hub.domain.usuario.dto;

import com.aluracursos.foro_hub.domain.usuario.entidad.Usuario;

public record DatosAutenticacionUsuario(
        String email,
        String clave
) {

    public DatosAutenticacionUsuario(Usuario usuario) {
        this(usuario.getEmail(), usuario.getClave());
    }
}
