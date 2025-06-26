package com.jangelcode.spring.app.service;

import com.jangelcode.spring.app.dto.PedidoDTO;
import com.jangelcode.spring.app.dto.PedidoRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService {
    PedidoDTO crearPedido(PedidoRequest request, MultipartFile comprobante);

    List<PedidoDTO> getPedidos();

    Page<PedidoDTO> findByFechaPedidoAfter(LocalDateTime fecha, Pageable pageable);

    PedidoDTO actualizarEstado(Long id, String nuevoEstado);
}
