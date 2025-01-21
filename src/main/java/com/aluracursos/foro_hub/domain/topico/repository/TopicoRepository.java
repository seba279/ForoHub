package com.aluracursos.foro_hub.domain.topico.repository;

import com.aluracursos.foro_hub.domain.topico.entidad.Topico;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByActivoTrue(Pageable paginacion);

    @Query("SELECT t FROM Topico t WHERE t.activo = true ORDER BY t.curso ASC")
    Page<Topico> findByCursoAsc(Pageable paginacion);

    @Query("SELECT t FROM Topico t WHERE t.activo ORDER BY t.fecha_creacion DESC")
    Page<Topico> findAllOrderByFechaCreacion(Pageable paginacion);

    @Query("SELECT COUNT(t) > 0 FROM Topico t WHERE t.titulo = :titulo")
    boolean existsByTitulo(@Param("titulo") String titulo);
}
