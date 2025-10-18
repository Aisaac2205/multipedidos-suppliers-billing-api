package com.multipedidos.proveedores.service;

import com.multipedidos.proveedores.dto.ProveedorDTO;
import com.multipedidos.proveedores.dto.ProveedorInputDTO;
import com.multipedidos.proveedores.model.Proveedor;
import com.multipedidos.proveedores.repository.ProveedorRepository;
import com.multipedidos.common.exceptions.DatosInvalidosException;
import com.multipedidos.common.exceptions.RecursoNoEncontradoException;
import com.multipedidos.common.utils.ValidadorCodigos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de proveedores.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    /**
     * Crea un nuevo proveedor.
     */
    @Transactional
    public ProveedorDTO crearProveedor(ProveedorInputDTO input) {
        log.info("Creando nuevo proveedor: {}", input.getNombre());

        // Validar email
        if (!ValidadorCodigos.validarEmail(input.getCorreo())) {
            throw new DatosInvalidosException("El formato del correo es inválido");
        }

        // Verificar que el correo no esté registrado
        if (proveedorRepository.existsByCorreo(input.getCorreo())) {
            throw new DatosInvalidosException("Ya existe un proveedor con ese correo");
        }

        Proveedor proveedor = Proveedor.builder()
                .nombre(input.getNombre())
                .correo(input.getCorreo())
                .build();

        Proveedor guardado = proveedorRepository.save(proveedor);
        log.info("Proveedor creado con ID: {}", guardado.getId());

        return mapearADTO(guardado);
    }

    /**
     * Obtiene todos los proveedores.
     */
    @Transactional(readOnly = true)
    public List<ProveedorDTO> listarProveedores() {
        log.info("Listando todos los proveedores");
        return proveedorRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un proveedor por ID.
     */
    @Transactional(readOnly = true)
    public ProveedorDTO obtenerProveedor(Long id) {
        log.info("Buscando proveedor con ID: {}", id);
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor", id));
        return mapearADTO(proveedor);
    }

    /**
     * Verifica si un proveedor existe.
     */
    public boolean existeProveedor(Long id) {
        return proveedorRepository.existsById(id);
    }

    /**
     * Mapea una entidad Proveedor a DTO.
     */
    private ProveedorDTO mapearADTO(Proveedor proveedor) {
        return ProveedorDTO.builder()
                .id(proveedor.getId())
                .nombre(proveedor.getNombre())
                .correo(proveedor.getCorreo())
                .build();
    }
}

