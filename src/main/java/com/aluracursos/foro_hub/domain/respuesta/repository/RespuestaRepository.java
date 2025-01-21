package com.aluracursos.foro_hub.domain.respuesta.repository;

import com.aluracursos.foro_hub.domain.respuesta.entidad.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
}
