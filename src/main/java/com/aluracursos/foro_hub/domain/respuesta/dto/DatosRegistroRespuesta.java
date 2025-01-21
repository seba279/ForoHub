package com.aluracursos.foro_hub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long topico,
        @NotNull
        LocalDateTime fecha_creacion,
        @NotNull
        Long autor
) {
}
