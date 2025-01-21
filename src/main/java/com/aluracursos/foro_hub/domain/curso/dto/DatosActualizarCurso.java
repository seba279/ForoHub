package com.aluracursos.foro_hub.domain.curso.dto;

import com.aluracursos.foro_hub.domain.curso.entidad.Categoria;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long id,
        String nombre,
        Categoria categoria
) {
}
