package com.aluracursos.foro_hub.infra.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<Map<String, Object>> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("error", "Recurso no encontrado");
    response.put("mensaje", ex.getMessage());
    response.put("estado", HttpStatus.NOT_FOUND.value());

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    String errorMessage = ex.getMessage();
    String duplicatedField = extractDuplicatedField(errorMessage);

    Map<String, Object> response = new HashMap<>();
    response.put("error", "Duplicación de campo");
    response.put("mensaje", duplicatedField != null
            ? "El valor ingresado ya existe para el campo " + duplicatedField
            : "El valor ingresado ya existe para un campo único");
    response.put("estado", HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  private String extractDuplicatedField(String errorMessage) {
    // Buscar el nombre del campo después de 'key ' y antes de la comilla simple
    String fieldName = errorMessage.split("for key '")[1].split("'")[0];
    // Eliminar prefijo de tabla si lo tiene
    return fieldName.contains(".") ? fieldName.split("\\.")[1] : fieldName;
  }
}
