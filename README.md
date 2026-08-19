# Tienda Zely — Backend

API REST para la gestión de una tienda: productos, clientes, ventas, números de serie y dashboard. Desarrollado con **Spring Boot 4** y **Java 21**.

## Stack tecnológico

| Tecnología | Uso |
|------------|-----|
| Spring Boot 4.0.6 | Framework principal |
| Spring Data JPA | Persistencia |
| PostgreSQL | Base de datos |
| MapStruct | Mapeo DTO ↔ entidad |
| Lombok | Reducción de boilerplate |
| Apache POI | Importación y exportación Excel (.xlsx) |
| Jakarta Validation | Validación de requests |

## Requisitos

- Java 21
- Maven 3.8+
- PostgreSQL 12+

## Configuración

Edita `src/main/resources/application.yaml` con los datos de tu base de datos:

```yaml
server:
  port: 8546

spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    database: POSTGRESQL
  datasource:
    url: jdbc:postgresql://localhost:5432/db-tienda
    username: tu_usuario
    password: tu_password
    driver-class-name: org.postgresql.Driver
```

> **Nota:** El esquema de base de datos utilizado es `sch_zely`. Con `ddl-auto: update`, Hibernate sincroniza las tablas automáticamente al iniciar.

## Ejecución

```bash
# Compilar
./mvnw clean compile

# Ejecutar
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8546`.

## Estructura del proyecto

```
src/main/java/com/tienda/zely/
├── config/          # CORS y configuración general
├── controller/      # Endpoints REST
├── dto/             # Objetos de transferencia por módulo
├── entity/          # Entidades JPA
├── exception/       # Excepciones y manejo global de errores
├── mapper/          # Interfaces MapStruct
├── repository/      # Repositorios Spring Data
├── service/         # Lógica de negocio
│   └── impl/
└── util/            # Constantes y utilidades (Excel, etc.)
```

## Módulos y endpoints

### Productos — `/product`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/active` | Lista productos activos |
| GET | `/desactive` | Lista productos inactivos |
| GET | `/downloadExcel` | Exporta productos activos a Excel (.xlsx) |
| POST | `/insert` | Registra un producto |
| POST | `/insertExcel` | Registro masivo desde Excel (.xlsx) |
| PUT | `/update?codigoProducto=` | Actualiza un producto |
| PUT | `/updateExcel` | Actualización masiva desde Excel (.xlsx) |
| PUT | `/active?codigoProducto=` | Reactiva un producto |
| DELETE | `/inactive/{id}` | Desactiva un producto |

**Formato Excel (.xlsx), primera fila = encabezados:**

- Insert: columnas `nombre`, `codigoTipoProducto`, `precio`
- Update: columnas `codigoProducto`, `nombre`, `codigoTipoProducto`, `precio`

Antes de persistir, el insert masivo valida duplicados y productos inactivos; el update masivo valida que cada ID exista.

### Números de serie — `/serial-number`

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/` | Registra un número de serie |
| GET | `/?serialNumber=` | Consulta por número de serie |
| GET | `/product?idProduct=` | Lista series de un producto |

### Clientes — `/person`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/getAll` | Clientes activos |
| GET | `/getDeactivated` | Clientes inactivos |
| POST | `/register` | Registra un cliente |
| PUT | `/update` | Actualiza un cliente |
| PATCH | `/activate` | Activa un cliente |
| DELETE | `/deactivate` | Desactiva un cliente |

### Ventas — `/sale`

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/register` | Registra una venta |
| GET | `/getAll` | Ventas activas |
| GET | `/getById?codigo=` | Detalle de una venta |
| PATCH | `/deactivate` | Anula una venta |

### Detalle de venta — `/detailSale`

Endpoints para consultar y gestionar líneas de detalle de ventas.

### Tipos — `/typeProduct`, `/typeSale`

Catálogos de tipos de producto y tipos de venta.

### Parámetros — `/parameter`

Parámetros de configuración para el registro de ventas.

### Dashboard — `/dashboard`

Estadísticas y resumen para el panel principal.

## Manejo de errores

`GlobalExceptionHandler` centraliza las respuestas de error:

| Excepción | HTTP | Uso |
|-----------|------|-----|
| `ResourceNotFoundException` | 404 | Recurso no encontrado |
| `ConflictException` | 409 | Reglas de negocio (Excel inválido, duplicados, etc.) |
| `MethodArgumentNotValidException` | 400 | Validación de body |
| `ConstraintViolationException` | 400 | Validación de parámetros |

Formato de respuesta de error:

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Fila 3: el producto 'LAPTOP' ya existe y esta activo",
  "path": "/product/insertExcel",
  "timestamp": "2026-08-17T12:00:00"
}
```

## CORS

El filtro `CORS` permite peticiones desde cualquier origen (`*`), necesario para el frontend servido en un puerto distinto.

## Tests

```bash
./mvnw test
```

## Proyecto relacionado

Frontend: [frontend-tienda-zely](../frontend-tienda-zely)
