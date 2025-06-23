package com.jangelcode.spring.app.service.impl;

import com.jangelcode.spring.app.service.UsuarioService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.jangelcode.spring.app.dto.UsuarioDTO;
import com.jangelcode.spring.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(com.jangelcode.spring.app.mapper.UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }
}
