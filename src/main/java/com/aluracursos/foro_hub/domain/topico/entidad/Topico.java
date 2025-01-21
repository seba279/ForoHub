package com.aluracursos.foro_hub.domain.topico.entidad;

import com.aluracursos.foro_hub.domain.topico.dto.DatosActualizarTopico;
import com.aluracursos.foro_hub.domain.topico.dto.DatosRegistroTopico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDateTime;


@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private Long autor;
    private Long curso;
    private Boolean activo;

    public Topico() {
    }


    public Topico(DatosRegistroTopico datosRegistroTopico) {
        this.activo = true;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.fecha_creacion = LocalDateTime.now();
        this.estado = Estado.ABIERTO;
        this.autor = datosRegistroTopico.autor();
        this.curso = datosRegistroTopico.curso();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }

    public Long getCurso() {
        return curso;
    }

    public void setCurso(Long curso) {
        this.curso = curso;
    }

    public void actualizarDatos(@Valid DatosActualizarTopico datosActualizarTopico) {

        if(datosActualizarTopico.mensaje() != null){
            this.mensaje = datosActualizarTopico.mensaje();
        }

        if(datosActualizarTopico.estado() != null){
            this.estado = datosActualizarTopico.estado();
        }

        this.fecha_creacion = LocalDateTime.now();

    }

    public void desactivarTopico() {
        this.activo = false;
    }
}
