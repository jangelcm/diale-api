package com.jangelcode.spring.app.dto;

import lombok.Data;

@Data
public class PedidoProductoDTO {
    private Long productoId;
    private String nombreProducto;
    private int cantidad;
    private Double precio;

}
