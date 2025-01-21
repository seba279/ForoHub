package com.aluracursos.foro_hub.domain.topico.dto;

import com.aluracursos.foro_hub.domain.topico.entidad.Topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(Long id, String titulo, String mensaje, LocalDateTime fecha_creacion, String estado, Long autor, Long curso) {

    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(), topico.getEstado().toString(), topico.getAutor(), topico.getCurso());
    }

}
