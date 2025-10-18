# MultiPedidos API - Proveedores y Facturación

Microservicio para gestión de proveedores y facturación con PostgreSQL.

## Puerto

8081

## Base de Datos

PostgreSQL

## Documentación API

- Swagger UI: http://localhost:8081/swagger-ui.html
- OpenAPI JSON: http://localhost:8081/api-docs

## Endpoints

### Proveedores
- POST /proveedores - Registrar proveedor
- GET /proveedores - Listar todos
- GET /proveedores/{id} - Obtener por ID

### Facturas
- POST /facturas - Crear factura (cálculo automático de total)
- GET /facturas - Listar todas
- GET /facturas/{id} - Obtener por ID

## Comunicación entre Servicios

Este microservicio se comunica con el Microservicio A (Clientes y Pedidos) para validar pedidos.

Configurar la URL en `.env`:
```
MICROSERVICE_CLIENTES_URL=http://localhost:8080
```

## Dependencias

Requiere:
- Common Library (multipedidos-common-lib)
- PostgreSQL
- Microservicio A (para validación de pedidos)

## Instalación Local

```bash
# 1. Instalar common-library primero
cd ../multipedidos-common-lib
mvn clean install

# 2. Compilar este servicio
mvn clean compile

# 3. Asegurar que Microservicio A esté corriendo en 8080

# 4. Ejecutar
mvn spring-boot:run
```

## Configuración

Copiar `.env.example` a `.env` y configurar variables de entorno.

## Despliegue en Railway

1. Crear nuevo servicio desde este repositorio
2. Conectar base de datos PostgreSQL
3. Configurar variables de entorno según `.env.example`
4. Configurar `MICROSERVICE_CLIENTES_URL` con la URL del Microservicio A desplegado
5. Railway detectará automáticamente el `railway.toml`

### Variables Requeridas en Railway

```
DATABASE_URL (o usar variables de PostgreSQL de Railway)
MICROSERVICE_CLIENTES_URL (URL del otro microservicio)
SPRING_PROFILES_ACTIVE=production
```

## Versión

1.0.0

