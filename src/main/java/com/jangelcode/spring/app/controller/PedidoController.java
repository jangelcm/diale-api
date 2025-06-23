package com.jangelcode.spring.app.controller;

import com.jangelcode.spring.app.dto.PedidoDTO;
import com.jangelcode.spring.app.dto.PedidoRequest;
import com.jangelcode.spring.app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestPart PedidoRequest request,
            @RequestPart("comprobante") MultipartFile comprobante) {
        PedidoDTO pedido = pedidoService.crearPedido(request, comprobante);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/mios")
    public ResponseEntity<List<PedidoDTO>> getPedidosUsuarioActual() {
        return ResponseEntity.ok(pedidoService.getPedidos());
    }

    @GetMapping("/paginado")
    public Page<PedidoDTO> getPedidosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPedido) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDateTime fechaDesde = fechaPedido.atStartOfDay();
        return pedidoService.findByFechaPedidoAfter(fechaDesde, pageable);
    }
}
