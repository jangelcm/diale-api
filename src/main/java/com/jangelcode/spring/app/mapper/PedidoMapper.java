package com.jangelcode.spring.app.mapper;

import com.jangelcode.spring.app.dto.PedidoDTO;
import com.jangelcode.spring.app.dto.PedidoProductoDTO;
import com.jangelcode.spring.app.entity.Pedido;
import com.jangelcode.spring.app.entity.PedidoProducto;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {
    public static PedidoDTO toDTO(Pedido entity) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsuario() != null ? entity.getUsuario().getUsername() : null);
        dto.setItems(entity.getItems() != null
                ? entity.getItems().stream().map(PedidoMapper::toDTO).collect(Collectors.toList())
                : null);
        dto.setDireccionEnvio(entity.getDireccionEnvio());
        dto.setTelefonoContacto(entity.getTelefonoContacto());
        dto.setComprobanteUrl(entity.getComprobanteUrl());
        dto.setFechaPedido(entity.getFechaPedido());
        return dto;
    }

    public static PedidoProductoDTO toDTO(PedidoProducto entity) {
        PedidoProductoDTO dto = new PedidoProductoDTO();
        dto.setProductoId(entity.getProducto() != null ? entity.getProducto().getId() : null);
        dto.setNombreProducto(entity.getProducto() != null ? entity.getProducto().getNombre() : null);
        dto.setCantidad(entity.getCantidad());
        dto.setPrecio(entity.getProducto().getPrecio());
        return dto;
    }

    public static List<PedidoDTO> toDTOList(List<Pedido> entities) {
        return entities.stream().map(PedidoMapper::toDTO).collect(Collectors.toList());
    }
}