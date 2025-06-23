package com.jangelcode.spring.app.service.impl;

import com.jangelcode.spring.app.dto.PedidoDTO;
import com.jangelcode.spring.app.dto.PedidoRequest;
import com.jangelcode.spring.app.entity.Pedido;
import com.jangelcode.spring.app.entity.PedidoProducto;
import com.jangelcode.spring.app.entity.ProductoMagistral;
import com.jangelcode.spring.app.entity.Usuario;
import com.jangelcode.spring.app.exception.ResourceNotFoundException;
import com.jangelcode.spring.app.mapper.PedidoMapper;
import com.jangelcode.spring.app.repository.PedidoRepository;
import com.jangelcode.spring.app.repository.ProductoMagistralRepository;
import com.jangelcode.spring.app.repository.UsuarioRepository;
import com.jangelcode.spring.app.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ProductoMagistralRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public PedidoDTO crearPedido(PedidoRequest request, MultipartFile comprobante) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDireccionEnvio(request.getDireccionEnvio());
        pedido.setTelefonoContacto(request.getTelefonoContacto());
        pedido.setFechaPedido(LocalDateTime.now());

        // Guardar comprobante si existe
        if (comprobante != null && !comprobante.isEmpty()) {
            String uploadDir = "public/comprobantes/";
            String fileName = UUID.randomUUID() + "-" + comprobante.getOriginalFilename();
            try {
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(comprobante.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                pedido.setComprobanteUrl("/public/comprobantes/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar el comprobante", e);
            }
        }

        // Crear los items del pedido con cantidad
        List<PedidoProducto> items = request.getItems().stream().map(itemReq -> {
            if (itemReq.getProductoId() == null) {
                throw new NullPointerException("Id del producto nulo");
            }
            ProductoMagistral producto = productoRepository.findById(itemReq.getProductoId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Producto no encontrado: " + itemReq.getProductoId()));
            PedidoProducto item = new PedidoProducto();
            item.setPedido(pedido);
            item.setProducto(producto);
            item.setCantidad(itemReq.getCantidad());
            return item;
        }).collect(Collectors.toList());

        pedido.setItems(items);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return PedidoMapper.toDTO(pedidoGuardado);
    }

    @Override
    public List<PedidoDTO> getPedidos() {
        List<Pedido> listPedidosProducto = pedidoRepository.findAll();

        return PedidoMapper.toDTOList(listPedidosProducto);
    }

    @Override
    public Page<PedidoDTO> findByFechaPedidoAfter(LocalDateTime fecha, Pageable pageable) {
        return pedidoRepository.findByFechaPedidoAfter(fecha, pageable)
                .map(PedidoMapper::toDTO);
    }
}
