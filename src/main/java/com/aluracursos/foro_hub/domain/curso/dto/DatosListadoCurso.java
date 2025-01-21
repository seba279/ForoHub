package com.aluracursos.foro_hub.domain.curso.dto;

import com.aluracursos.foro_hub.domain.curso.entidad.Curso;

public record DatosListadoCurso(
        Long id,
        String nombre,
        String categoria
) {
    public DatosListadoCurso(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria().toString());
    }
}
