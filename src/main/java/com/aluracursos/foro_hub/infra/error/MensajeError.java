package com.aluracursos.foro_hub.infra.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class MensajeError {
    private String mensaje;
    private String detalle;
    private int codigo;
}
