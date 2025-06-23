package com.jangelcode.spring.app.mapper;

import com.jangelcode.spring.app.entity.ProductoMagistral;
import com.jangelcode.spring.app.dto.ProductoMagistralDTO;

public class ProductoMagistralMapper {
    public static ProductoMagistralDTO toDTO(ProductoMagistral entity) {
        ProductoMagistralDTO dto = new ProductoMagistralDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setImagenUrl(entity.getImagenUrl());
        return dto;
    }

    public static ProductoMagistral toEntity(ProductoMagistralDTO dto) {
        ProductoMagistral entity = new ProductoMagistral();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());
        entity.setImagenUrl(dto.getImagenUrl());
        return entity;
    }
}
