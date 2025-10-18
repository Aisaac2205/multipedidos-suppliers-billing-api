package com.multipedidos.proveedores.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI 3.0 para documentación automática de la API REST.
 * Sigue las mejores prácticas del estándar OpenAPI Specification (OAS).
 * 
 * Puerto del Servicio: 8081 (Definido en application.yml)
 * 
 * Estrategia de Puertos:
 * - 8080: Microservicio A (Clientes y Pedidos)
 * - 8081: Microservicio B (Proveedores y Facturación)
 * 
 * Documentación disponible en:
 * - Swagger UI: http://localhost:8081/swagger-ui.html
 * - OpenAPI JSON: http://localhost:8081/api-docs
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MultiPedidos API - Gestión de Proveedores y Facturación")
                        .version("1.0.0")
                        .description("""
                                API RESTful para la gestión de proveedores y facturación.
                                
                                Funcionalidades principales:
                                - Registro y consulta de proveedores
                                - Creación de facturas con referencias a pedidos
                                - Integración con Microservicio A para validación de pedidos
                                - Aplicación de descuentos sobre totales de facturación
                                - Comunicación entre microservicios mediante HTTP/REST
                                
                                Base de Datos: PostgreSQL
                                Puerto del Servicio: 8081
                                
                                Especificación OpenAPI: 3.0.3
                                """)
                        .contact(new Contact()
                                .name("Equipo de Desarrollo - MultiPedidos S.A.")
                                .email("dev@multipedidos.com")
                                .url("https://multipedidos.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de Desarrollo Local"),
                        new Server()
                                .url("http://localhost:" + serverPort + "/api/v1")
                                .description("Servidor de Desarrollo con versionado"),
                        new Server()
                                .url("https://api.multipedidos.com/proveedores")
                                .description("Servidor de Producción (Pendiente)")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa del proyecto")
                        .url("https://github.com/multipedidos/docs"));
    }
}

