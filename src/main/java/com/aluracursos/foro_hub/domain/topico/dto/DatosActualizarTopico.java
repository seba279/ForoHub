package com.aluracursos.foro_hub.domain.topico.dto;

import com.aluracursos.foro_hub.domain.topico.entidad.Estado;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarTopico(

        @NotNull
        Long id,
        String mensaje,
        LocalDateTime fecha_creacion,
        Estado estado
) {

}
