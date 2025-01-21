package com.aluracursos.foro_hub.domain.usuario.dto;


import jakarta.validation.constraints.NotNull;


public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String email,
        Long perfiles
) {
}
