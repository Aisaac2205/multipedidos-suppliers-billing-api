package com.multipedidos.proveedores.service;

import com.multipedidos.proveedores.client.PedidosClient;
import com.multipedidos.proveedores.dto.FacturaDTO;
import com.multipedidos.proveedores.dto.FacturaInputDTO;
import com.multipedidos.proveedores.dto.PedidoReferenciaDTO;
import com.multipedidos.proveedores.model.Factura;
import com.multipedidos.proveedores.model.PedidoReferencia;
import com.multipedidos.proveedores.repository.FacturaRepository;
import com.multipedidos.common.exceptions.DatosInvalidosException;
import com.multipedidos.common.exceptions.RecursoNoEncontradoException;
import com.multipedidos.common.utils.CalculadoraDescuentos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de facturas.
 * Puede consultar pedidos del microservicio A para validar.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ProveedorService proveedorService;
    private final PedidosClient pedidosClient;

    /**
     * Crea una nueva factura.
     */
    @Transactional
    public FacturaDTO crearFactura(FacturaInputDTO input) {
        log.info("Creando nueva factura para proveedor ID: {}", input.getProveedorId());

        // Verificar que el proveedor existe
        if (!proveedorService.existeProveedor(input.getProveedorId())) {
            throw new DatosInvalidosException("El proveedor con ID " + input.getProveedorId() + " no existe");
        }

        // Validar que haya pedidos
        if (input.getPedidos() == null || input.getPedidos().isEmpty()) {
            throw new DatosInvalidosException("La factura debe tener al menos un pedido");
        }

        // Validar que los pedidos existen en el microservicio A (opcional pero recomendado)
        for (PedidoReferenciaDTO pedidoRef : input.getPedidos()) {
            boolean existe = pedidosClient.existePedido(pedidoRef.getPedidoId());
            if (!existe) {
                log.warn("Pedido {} no encontrado en microservicio A, pero se continuará con la factura", 
                        pedidoRef.getPedidoId());
            }
        }

        // Calcular total de la factura
        BigDecimal totalFactura = input.getPedidos().stream()
                .map(PedidoReferenciaDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Usar la librería común para aplicar descuentos adicionales al total
        BigDecimal totalConDescuento = CalculadoraDescuentos.aplicarDescuentoPorMonto(totalFactura);

        // Convertir DTOs a entidades
        List<PedidoReferencia> referencias = input.getPedidos().stream()
                .map(dto -> PedidoReferencia.builder()
                        .pedidoId(dto.getPedidoId())
                        .total(dto.getTotal())
                        .build())
                .collect(Collectors.toList());

        // Crear factura
        Factura factura = Factura.builder()
                .proveedorId(input.getProveedorId())
                .pedidos(referencias)
                .totalFactura(totalConDescuento)
                .estado(Factura.EstadoFactura.PENDIENTE)
                .build();

        Factura guardada = facturaRepository.save(factura);
        log.info("Factura creada con ID: {} - Total: {}", guardada.getId(), totalConDescuento);

        return mapearADTO(guardada);
    }

    /**
     * Obtiene todas las facturas.
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> listarFacturas() {
        log.info("Listando todas las facturas");
        return facturaRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una factura por ID.
     */
    @Transactional(readOnly = true)
    public FacturaDTO obtenerFactura(Long id) {
        log.info("Buscando factura con ID: {}", id);
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Factura", id));
        return mapearADTO(factura);
    }

    /**
     * Obtiene facturas de un proveedor específico.
     */
    @Transactional(readOnly = true)
    public List<FacturaDTO> listarFacturasPorProveedor(Long proveedorId) {
        log.info("Listando facturas del proveedor ID: {}", proveedorId);
        return facturaRepository.findByProveedorId(proveedorId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una entidad Factura a DTO.
     */
    private FacturaDTO mapearADTO(Factura factura) {
        List<PedidoReferenciaDTO> pedidosDTO = factura.getPedidos().stream()
                .map(p -> PedidoReferenciaDTO.builder()
                        .pedidoId(p.getPedidoId())
                        .total(p.getTotal())
                        .build())
                .collect(Collectors.toList());

        return FacturaDTO.builder()
                .id(factura.getId())
                .proveedorId(factura.getProveedorId())
                .pedidos(pedidosDTO)
                .totalFactura(factura.getTotalFactura())
                .build();
    }
}

