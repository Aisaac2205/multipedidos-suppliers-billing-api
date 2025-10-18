package com.multipedidos.proveedores.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para referencias a pedidos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoReferenciaDTO {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private BigDecimal total;
}

