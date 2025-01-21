package com.aluracursos.foro_hub.domain.curso.dto;

import com.aluracursos.foro_hub.domain.curso.entidad.Categoria;

public record DatosRespuestaCurso(
        Long id,
        String nombre,
        Categoria categoria
) {
}
