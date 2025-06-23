package com.jangelcode.spring.app.service;

import com.jangelcode.spring.app.dto.ProductoMagistralDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductoMagistralService {
    List<ProductoMagistralDTO> findAll();

    Page<ProductoMagistralDTO> findAll(Pageable pageable);

    ProductoMagistralDTO findById(Long id);

    ProductoMagistralDTO create(ProductoMagistralDTO dto);

    ProductoMagistralDTO update(Long id, ProductoMagistralDTO dto);

    void delete(Long id);

    Page<ProductoMagistralDTO> buscarPorFiltros(String nombre, Double minPrecio, Double maxPrecio, Pageable pageable);

    String guardarImagen(MultipartFile file) throws IOException;
}
