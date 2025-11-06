package com.multipedidos.proveedores.controller;

import com.multipedidos.proveedores.dto.ProveedorDTO;
import com.multipedidos.proveedores.dto.ProveedorInputDTO;
import com.multipedidos.proveedores.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de proveedores.
 */
@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Tag(name = "Proveedores", description = "API para gestión de proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    @Operation(summary = "Registrar un proveedor", description = "Registra un nuevo proveedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ProveedorDTO> crearProveedor(@Valid @RequestBody ProveedorInputDTO input) {
        ProveedorDTO proveedor = proveedorService.crearProveedor(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedor);
    }

    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Obtiene la lista completa de proveedores")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores obtenida correctamente")
    public ResponseEntity<List<ProveedorDTO>> listarProveedores() {
        List<ProveedorDTO> proveedores = proveedorService.listarProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un proveedor por ID", description = "Obtiene los detalles de un proveedor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    public ResponseEntity<ProveedorDTO> obtenerProveedor(@PathVariable Long id) {
        ProveedorDTO proveedor = proveedorService.obtenerProveedor(id);
        return ResponseEntity.ok(proveedor);
    }
}

