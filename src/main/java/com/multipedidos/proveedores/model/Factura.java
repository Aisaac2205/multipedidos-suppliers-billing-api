package com.multipedidos.proveedores.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Factura para almacenar información de facturas.
 */
@Entity
@Table(name = "facturas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El proveedor es obligatorio")
    @Column(name = "proveedor_id", nullable = false)
    private Long proveedorId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "factura_id")
    @Builder.Default
    private List<PedidoReferencia> pedidos = new ArrayList<>();

    @Column(name = "total_factura", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFactura;

    @Column(name = "fecha_factura", updatable = false)
    private LocalDateTime fechaFactura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoFactura estado = EstadoFactura.PENDIENTE;

    @PrePersist
    protected void onCreate() {
        fechaFactura = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoFactura.PENDIENTE;
        }
    }

    public enum EstadoFactura {
        PENDIENTE,
        PAGADA,
        CANCELADA
    }
}

