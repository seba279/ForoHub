package com.aluracursos.foro_hub.domain.respuesta.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarRespuesta(
        @NotNull
        Long id,
        String mensaje,
        Boolean solucion
) {
}
