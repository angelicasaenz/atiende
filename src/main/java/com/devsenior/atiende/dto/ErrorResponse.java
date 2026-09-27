package com.devsenior.atiende.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    private int codigo;
    private String error;
    private String mensaje;
    private LocalDateTime fecha;

    public ErrorResponse() {
    }

    public ErrorResponse(int codigo, String error, String mensaje, LocalDateTime fecha) {
        this.codigo = codigo;
        this.error = error;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public static ErrorResponse of(int codigo, String error, String mensaje) {
        return new ErrorResponse(codigo, error, mensaje, LocalDateTime.now());
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
