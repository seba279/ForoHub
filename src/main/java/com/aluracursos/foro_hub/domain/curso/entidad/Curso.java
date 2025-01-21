package com.aluracursos.foro_hub.domain.curso.entidad;


import com.aluracursos.foro_hub.domain.curso.dto.DatosActualizarCurso;
import com.aluracursos.foro_hub.domain.curso.dto.DatosRegistroCurso;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Curso")
@Table(name = "cursos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nombre;
    @Enumerated(EnumType.STRING)
    Categoria categoria;

    public Curso() {}

    public Curso(DatosRegistroCurso datosRegistroCurso) {
        this.nombre = datosRegistroCurso.nombre();
        this.categoria = datosRegistroCurso.categoria();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void actualizarDatos(@Valid DatosActualizarCurso datosActualizarCurso) {

        if(datosActualizarCurso.nombre() != null){
            this.nombre = datosActualizarCurso.nombre();
        }

        if(datosActualizarCurso.categoria() != null){
            this.categoria = datosActualizarCurso.categoria();
        }
    }
}
