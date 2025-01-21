package com.aluracursos.foro_hub.infra.error;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ManejoDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();

        return ResponseEntity.badRequest().body(errores);
    }



    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> manejarViolacionDeRestriccion(SQLIntegrityConstraintViolationException e) {

        String mensaje = e.getMessage();
        //String entradaDuplicada = extraerEntradaDuplicada(mensaje);
        String campo = extraerCampoDeError(mensaje);
        Integer codigoError = HttpStatus.BAD_REQUEST.value();

        ErrorResponse errorResponse = new ErrorResponse(
                codigoError,
                "Violación de restricción de integridad",
                "No se puede duplicar el campo [" + campo + "]"
                //"El campo que causó el error es " + campo,

        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



    private String extraerEntradaDuplicada(String mensaje) {
        // Patrón para capturar el valor duplicado entre comillas simples
        Pattern pattern = Pattern.compile("Duplicate entry '(.+?)'");
        Matcher matcher = pattern.matcher(mensaje);
        if (matcher.find()) {
            return matcher.group(1); // Captura la entrada duplicada
        }
        return "Entrada desconocida"; // Si no encuentra el valor
    }

    private String extraerCampoDeError(String mensaje) {
        // Patrón para encontrar el nombre del campo en el mensaje
        Pattern pattern = Pattern.compile("key '(.+?)'");
        Matcher matcher = pattern.matcher(mensaje);
        if (matcher.find()) {
            String campoCompleto = matcher.group(1); // Retorna algo como 'topicos.mensaje'
            // Si el campo contiene un punto, toma solo la parte después del último punto
            if (campoCompleto.contains(".")) {
                return campoCompleto.substring(campoCompleto.lastIndexOf(".") + 1);
            }
            return campoCompleto; // Retorna el campo completo si no hay punto
        }
        return "Campo desconocido"; // Si no encuentra el campo
    }



    private record DatosErrorValidacion(String campo, String error){

        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
