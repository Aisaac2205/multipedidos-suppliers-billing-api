package com.multipedidos.proveedores.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para permitir
 * peticiones desde el frontend y otras aplicaciones externas.
 * 
 * Sigue las mejores prácticas de seguridad permitiendo orígenes específicos.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:3000",           // Frontend Next.js desarrollo
                        "http://localhost:3001",           // Frontend alternativo
                        "http://localhost:4200",           // Angular desarrollo
                        "https://multipedidos.com",        // Producción
                        "https://www.multipedidos.com"     // Producción con www
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);  // Cache preflight por 1 hora
    }
}

