package com.aluracursos.foro_hub.infra.seguridad;

import com.aluracursos.foro_hub.domain.usuario.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Obtener el token del header
        var authHeader = request.getHeader("Authorization");
        if(authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token);
            if (subject != null){
                //Token valido
                var usuario = usuarioRepository.findByEmail(subject);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                            usuario.getAuthorities());// Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
