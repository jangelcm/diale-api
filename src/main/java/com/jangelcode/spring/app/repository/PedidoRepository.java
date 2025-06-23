package com.jangelcode.spring.app.repository;

import com.jangelcode.spring.app.entity.Pedido;
import com.jangelcode.spring.app.entity.PedidoProducto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM PedidoProducto p")
    List<PedidoProducto> getAllPedidoProducto();

    Page<Pedido> findByFechaPedidoAfter(LocalDateTime fecha, Pageable pageable);
}
