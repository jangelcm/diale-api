package com.jangelcode.spring.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequest {
    private List<PedidoItemRequest> items;
    private String direccionEnvio;
    private String telefonoContacto;

}
