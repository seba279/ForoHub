package com.aluracursos.foro_hub.domain.usuario.repository;

import com.aluracursos.foro_hub.domain.usuario.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String username);
}
