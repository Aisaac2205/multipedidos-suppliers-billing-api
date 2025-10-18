package com.multipedidos.proveedores.repository;

import com.multipedidos.proveedores.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Proveedor.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    
    /**
     * Busca un proveedor por su correo electrónico.
     */
    Optional<Proveedor> findByCorreo(String correo);
    
    /**
     * Verifica si existe un proveedor con el correo dado.
     */
    boolean existsByCorreo(String correo);
}

