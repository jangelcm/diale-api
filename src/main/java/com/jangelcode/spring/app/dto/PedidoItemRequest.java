package com.jangelcode.spring.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoItemRequest {
    private Long productoId;
    private int cantidad;
}
