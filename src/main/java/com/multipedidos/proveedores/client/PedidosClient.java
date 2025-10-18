package com.multipedidos.proveedores.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cliente para comunicación con el microservicio de Clientes y Pedidos.
 */
@Component
@Slf4j
public class PedidosClient {

    private final WebClient webClient;

    public PedidosClient(@Value("${microservice.clientes-pedidos.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Obtiene un pedido del microservicio A.
     */
    public PedidoDTO obtenerPedido(Long pedidoId) {
        try {
            log.info("Consultando pedido {} en microservicio A", pedidoId);
            return webClient.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .bodyToMono(PedidoDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pedido {} no encontrado en microservicio A", pedidoId);
            return null;
        } catch (Exception e) {
            log.error("Error al consultar pedido {}: {}", pedidoId, e.getMessage());
            return null;
        }
    }

    /**
     * Verifica si un pedido existe en el microservicio A.
     */
    public boolean existePedido(Long pedidoId) {
        try {
            PedidoDTO pedido = obtenerPedido(pedidoId);
            return pedido != null;
        } catch (Exception e) {
            log.error("Error al verificar existencia del pedido {}: {}", pedidoId, e.getMessage());
            return false;
        }
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

