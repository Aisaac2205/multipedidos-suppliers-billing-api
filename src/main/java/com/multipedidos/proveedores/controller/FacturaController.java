package com.multipedidos.proveedores.controller;

import com.multipedidos.proveedores.dto.FacturaDTO;
import com.multipedidos.proveedores.dto.FacturaInputDTO;
import com.multipedidos.proveedores.service.FacturaService;
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
 * Controlador REST para gestión de facturas.
 */
@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "API para gestión de facturas")
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    @Operation(summary = "Registrar una factura", description = "Crea una nueva factura con cálculo automático de total y descuentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Factura registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<FacturaDTO> crearFactura(@Valid @RequestBody FacturaInputDTO input) {
        FacturaDTO factura = facturaService.crearFactura(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }

    @GetMapping
    @Operation(summary = "Listar todas las facturas", description = "Obtiene la lista completa de facturas")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    public ResponseEntity<List<FacturaDTO>> listarFacturas() {
        List<FacturaDTO> facturas = facturaService.listarFacturas();
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID", description = "Obtiene los detalles de una factura específica y su total")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura encontrada"),
            @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<FacturaDTO> obtenerFactura(@PathVariable Long id) {
        FacturaDTO factura = facturaService.obtenerFactura(id);
        return ResponseEntity.ok(factura);
    }
}

