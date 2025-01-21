package com.aluracursos.foro_hub.infra.error;


import lombok.Getter;
@Getter
public class ErrorResponse {
  private Integer codigoError;
  private String error;
  private String detalle;

  public ErrorResponse(Integer codigoError, String error, String detalle) {
    this.codigoError = codigoError;
    this.error = error;
    this.detalle = detalle;
  }

  public Integer getCodigoError() {
    return codigoError;
  }

  public void setCodigoError(Integer codigoError) {
    this.codigoError = codigoError;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getDetalle() {
    return detalle;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }
}
