package com.aluracursos.foro_hub.domain.respuesta.dto;

import com.aluracursos.foro_hub.domain.respuesta.entidad.Respuesta;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(Long id, String mensaje, Long topico, LocalDateTime fecha_creacion, Long autor, Boolean solucion) {

    public DatosListadoRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico(), respuesta.getFecha_creacion(), respuesta.getAutor(), respuesta.getSolucion());
    }
}
