package com.jangelcode.spring.app.entity;

public enum EstadoPedido {
    EN_PROCESO(1),
    ACEPTADO(2),
    EN_CAMINO(3),
    LISTO_PARA_RECOGER(4),
    ENTREGADO(5),
    CANCELADO(6);

    private final int codigo;

    EstadoPedido(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
