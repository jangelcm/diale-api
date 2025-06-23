package com.jangelcode.spring.app.exception;

import java.util.List;

public class RestApiException {
    private String titulo;
    private String detalle;
    private List<String[]> errores;
    private int estado;

    public RestApiException(String titulo, String detalle, List<String[]> errores, int estado) {
        this.titulo = titulo;
        this.detalle = detalle;
        this.errores = errores;
        this.estado = estado;
    }

    public RestApiException(String titulo, String detalle, int estado) {
        this.titulo = titulo;
        this.detalle = detalle;
        this.estado = estado;
    }

    // Constructor anterior para compatibilidad (opcional, puedes eliminarlo si no
    // se usa)
    public RestApiException(String titulo, String detalle, List<String[]> errores) {
        this(titulo, detalle, errores, 400);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public List<String[]> getErrores() {
        return errores;
    }

    public void setErrores(List<String[]> errores) {
        this.errores = errores;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
