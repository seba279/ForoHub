package com.aluracursos.foro_hub.domain.usuario.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull
        Long id,
        String nombre,
        Long perfiles
) {
}
