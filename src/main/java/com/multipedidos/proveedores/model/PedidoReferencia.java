package com.multipedidos.proveedores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidad que almacena la referencia a un pedido del microservicio A.
 */
@Entity
@Table(name = "pedidos_referencias")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoReferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio")
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
}

