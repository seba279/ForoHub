package com.aluracursos.foro_hub.domain.curso.repository;

import com.aluracursos.foro_hub.domain.curso.entidad.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface CursoRepository extends JpaRepository<Curso, Long> {
    Page<Curso> findAll(Pageable paginacion);

    @Query("SELECT c FROM Curso c ORDER BY c.categoria ASC")
    Page<Curso> findAllOrderByCategoria(Pageable paginacion);
}
