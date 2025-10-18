package com.multipedidos.proveedores.repository;

import com.multipedidos.proveedores.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Factura.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    
    /**
     * Busca todas las facturas de un proveedor específico.
     */
    List<Factura> findByProveedorId(Long proveedorId);
    
    /**
     * Busca facturas por estado.
     */
    List<Factura> findByEstado(Factura.EstadoFactura estado);
}

