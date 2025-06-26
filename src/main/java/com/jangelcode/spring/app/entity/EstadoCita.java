package com.jangelcode.spring.app.entity;

public enum EstadoCita {
    PENDIENTE(1),
    CONFIRMADA(2),
    CANCELADA(3);

    private final int codigo;

    EstadoCita(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
