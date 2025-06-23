package com.jangelcode.spring.app.controller;

import com.jangelcode.spring.app.dto.ProductoMagistralDTO;
import com.jangelcode.spring.app.service.ProductoMagistralService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Tag(name = "Productos Magistrales", description = "Operaciones de productos magistrales de podología")
@RestController
@RequestMapping("/api/productos-magistrales")
public class ProductoMagistralController {
    @Autowired
    private ProductoMagistralService service;

    @Operation(summary = "Obtener todos los productos magistrales")
    @ApiResponse(responseCode = "200", description = "Listado de productos magistrales")
    @GetMapping
    public List<ProductoMagistralDTO> getAll() {
        return service.findAll();
    }

    @Operation(summary = "Obtener productos magistrales paginados y filtrados")
    @ApiResponse(responseCode = "200", description = "Listado paginado y filtrado de productos magistrales")
    @GetMapping("/paginado")
    public Page<ProductoMagistralDTO> getAllPaged(
            @Parameter(description = "Número de página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Nombre del producto (búsqueda parcial)") @RequestParam(required = false) String nombre,
            @Parameter(description = "Precio mínimo") @RequestParam(required = false) Double minPrecio,
            @Parameter(description = "Precio máximo") @RequestParam(required = false) Double maxPrecio) {
        Pageable pageable = PageRequest.of(page, size);
        return service.buscarPorFiltros(nombre, minPrecio, maxPrecio, pageable);
    }

    @Operation(summary = "Obtener producto magistral por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoMagistralDTO> getById(
            @Parameter(description = "ID del producto") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crear un producto magistral")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping
    public ResponseEntity<ProductoMagistralDTO> create(@RequestBody @Valid ProductoMagistralDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Operation(summary = "Actualizar un producto magistral")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoMagistralDTO> update(
            @Parameter(description = "ID del producto") @PathVariable Long id,
            @RequestBody @Valid ProductoMagistralDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Eliminar un producto magistral")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID del producto") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImagen(@RequestParam("file") MultipartFile file)
            throws IOException {
        String url = service.guardarImagen(file);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
