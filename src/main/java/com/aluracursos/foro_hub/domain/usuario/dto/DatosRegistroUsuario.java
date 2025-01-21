package com.aluracursos.foro_hub.domain.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String clave,
        @NotNull
        Long perfiles
) {
}
