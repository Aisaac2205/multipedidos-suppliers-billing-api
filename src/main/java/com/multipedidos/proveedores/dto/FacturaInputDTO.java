package com.multipedidos.proveedores.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para entrada de datos de Factura.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacturaInputDTO {

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Long proveedorId;

    @NotEmpty(message = "La factura debe tener al menos un pedido")
    @Valid
    private List<PedidoReferenciaDTO> pedidos;
}

