package com.jangelcode.spring.app.service.impl;

import com.jangelcode.spring.app.dto.ProductoMagistralDTO;
import com.jangelcode.spring.app.entity.ProductoMagistral;
import com.jangelcode.spring.app.exception.ResourceNotFoundException;
import com.jangelcode.spring.app.mapper.ProductoMagistralMapper;
import com.jangelcode.spring.app.repository.ProductoMagistralRepository;
import com.jangelcode.spring.app.service.ProductoMagistralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductoMagistralServiceImpl implements ProductoMagistralService {
    @Autowired
    private ProductoMagistralRepository repository;

    @Override
    public List<ProductoMagistralDTO> findAll() {
        return repository.findAll().stream().map(ProductoMagistralMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Page<ProductoMagistralDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ProductoMagistralMapper::toDTO);
    }

    @Override
    public ProductoMagistralDTO findById(Long id) {
        ProductoMagistral entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id del producto no encontrado"));
        return ProductoMagistralMapper.toDTO(entity);
    }

    @Override
    public ProductoMagistralDTO create(ProductoMagistralDTO dto) {
        ProductoMagistral entity = ProductoMagistralMapper.toEntity(dto);
        entity.setId(null);
        return ProductoMagistralMapper.toDTO(repository.save(entity));
    }

    @Override
    public ProductoMagistralDTO update(Long id, ProductoMagistralDTO dto) {
        Optional<ProductoMagistral> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Producto no encontrado para actualizar");
        }
        ProductoMagistral entity = optional.get();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());
        entity.setImagenUrl(dto.getImagenUrl());
        return ProductoMagistralMapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado para eliminar");
        }
        repository.deleteById(id);
    }

    @Override
    public Page<ProductoMagistralDTO> buscarPorFiltros(String nombre, Double minPrecio, Double maxPrecio,
            Pageable pageable) {
        return repository.buscarPorFiltros(nombre, minPrecio, maxPrecio, pageable)
                .map(ProductoMagistralMapper::toDTO);
    }

    @Override
    public String guardarImagen(MultipartFile file) throws IOException {
        String uploadDir = "public/preparados-magistrales/";
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/public/preparados-magistrales/" + fileName;
    }
}
