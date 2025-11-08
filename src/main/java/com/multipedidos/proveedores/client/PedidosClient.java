package com.multipedidos.proveedores.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipedidos.common.exceptions.IntegracionMicroserviciosException;
import com.multipedidos.common.utils.IntegradorMicroservicios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cliente para comunicación con el microservicio de Clientes y Pedidos utilizando
 * la utilería del componente C.
 */
@Component
public class PedidosClient {

    private static final Logger log = LoggerFactory.getLogger(PedidosClient.class);

    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public PedidosClient(ObjectMapper objectMapper,
                         @Value("${microservice.clientes-pedidos.url:http://localhost:8080}") String baseUrl) {
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
    }

    /**
     * Obtiene un pedido del microservicio A.
     */
    public PedidoDTO obtenerPedido(Long pedidoId) {
        try {
            log.info("Consultando pedido {} en microservicio A", pedidoId);
            Optional<String> respuesta = IntegradorMicroservicios.obtenerPedidoJson(baseUrl, pedidoId);
            return respuesta
                    .map(json -> parsearPedido(json, pedidoId))
                    .orElse(null);
        } catch (IntegracionMicroserviciosException e) {
            log.error("Error de integración al consultar pedido {}: {}", pedidoId, e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Error inesperado al consultar pedido {}: {}", pedidoId, e.getMessage());
            return null;
        }
    }

    private PedidoDTO parsearPedido(String json, Long pedidoId) {
        try {
            return objectMapper.readValue(json, PedidoDTO.class);
        } catch (Exception e) {
            log.error("No se pudo parsear el pedido {} devuelto por el microservicio A", pedidoId, e);
            return null;
        }
    }

    /**
     * Verifica si un pedido existe en el microservicio A.
     */
    public boolean existePedido(Long pedidoId) {
        PedidoDTO pedido = obtenerPedido(pedidoId);
        return pedido != null;
    }

    /**
     * DTO para recibir información de pedidos del microservicio A.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PedidoDTO {
        private Long id;
        private Long clienteId;
        private List<ProductoDTO> productos;
        private BigDecimal total;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoDTO {
        private String nombre;
        private BigDecimal precio;
    }
}


