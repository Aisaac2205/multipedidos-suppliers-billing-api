package com.multipedidos.proveedores.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para salida de datos de Factura.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Long id;
    private Long proveedorId;
    private List<PedidoReferenciaDTO> pedidos;
    private BigDecimal totalFactura;
}

