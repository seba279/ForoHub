package com.aluracursos.foro_hub.domain.usuario.entidad;


import com.aluracursos.foro_hub.domain.usuario.dto.DatosActualizarUsuario;
import com.aluracursos.foro_hub.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String clave;
    private Long perfiles;

    public Usuario() {
    }

    public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
        this.id = datosRegistroUsuario.id();
        this.nombre = datosRegistroUsuario.nombre();
        this.email = datosRegistroUsuario.email();
        this.clave = datosRegistroUsuario.clave();
        this.perfiles = datosRegistroUsuario.perfiles();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(Long perfiles) {
        this.perfiles = perfiles;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizarDatos(@Valid DatosActualizarUsuario datosActualizarUsuario) {

        if(datosActualizarUsuario.nombre() != null){
            this.nombre = datosActualizarUsuario.nombre();
        }

        if(datosActualizarUsuario.perfiles() != null){
            this.perfiles = datosActualizarUsuario.perfiles();
        }

    }
}
