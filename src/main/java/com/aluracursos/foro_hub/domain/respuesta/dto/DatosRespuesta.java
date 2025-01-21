package com.aluracursos.foro_hub.domain.respuesta.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRespuesta(
        @NotNull
        Long id,
        String mensaje,
        Long topico,
        LocalDateTime fecha_creacion,
        Long autor,
        Boolean solucion
) {
}
