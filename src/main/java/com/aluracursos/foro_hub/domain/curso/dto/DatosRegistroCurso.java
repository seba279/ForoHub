package com.aluracursos.foro_hub.domain.curso.dto;

import com.aluracursos.foro_hub.domain.curso.entidad.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroCurso(
        @NotBlank
        String nombre,
        @NotNull
        Categoria categoria
) {
}
