package com.aluracursos.foro_hub.domain.respuesta.entidad;


import com.aluracursos.foro_hub.domain.respuesta.dto.DatosActualizarRespuesta;
import com.aluracursos.foro_hub.domain.respuesta.dto.DatosRegistroRespuesta;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private Long topico;
    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
    private Long autor;
    private Boolean solucion;

    public Respuesta() {
    }

    public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta) {
        this.mensaje = datosRegistroRespuesta.mensaje();
        this.topico = datosRegistroRespuesta.topico();
        this.fecha_creacion = datosRegistroRespuesta.fecha_creacion();
        this.autor = datosRegistroRespuesta.autor();
        this.solucion = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getTopico() {
        return topico;
    }

    public void setTopico(Long topico) {
        this.topico = topico;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }

    public void actualizarDatos(@Valid DatosActualizarRespuesta datosActualizarRespuesta) {

        if(datosActualizarRespuesta.mensaje() != null){
            this.mensaje = datosActualizarRespuesta.mensaje();
        }

        if(datosActualizarRespuesta.solucion() != null){
            this.solucion = datosActualizarRespuesta.solucion();
        }
    }
}
