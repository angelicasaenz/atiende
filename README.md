# Atiende · Fase 1: Recepción de tickets

> *Nota: Este proyecto fue desarrollado utilizando herramientas de Inteligencia Artificial (IA).*

## ¿Qué es el proyecto?
**Atiende** es una solución backend desarrollada con Spring Boot para **Grano Alto Café** (tienda online de café de especialidad). Su propósito en la **Fase 1** es centralizar los mensajes recibidos a través de distintos canales (WhatsApp, correo electrónico y chat web) en un único sistema de tickets con estado y prioridad, permitiendo gestionar y dar seguimiento a los mensajes pendientes por atender.

---

## Requisitos
- **Java:** JDK 17 o superior.
- **Gestor de dependencias:** Maven 3.9+ (o utilizar el wrapper incluido `./mvnw` / `mvnw.cmd`).
- **Base de datos:** MySQL 8.x en ejecución con la siguiente configuración predeterminada (definida en `src/main/resources/application.properties`):
  - **Host / Puerto:** `localhost:3306`
  - **Base de datos:** `atiende_db` (se crea automáticamente si no existe con `createDatabaseIfNotExist=true`)
  - **Usuario:** `root`
  - **Contraseña:** `123456`

---

## Cómo ejecutarlo

1. **Asegurar que el servicio de MySQL esté corriendo** en el puerto 3306 con las credenciales indicadas.
2. **Ejecutar la aplicación** con el wrapper de Maven:
   - En Linux / macOS:
     ```bash
     ./mvnw spring-boot:run
     ```
   - En Windows (PowerShell):
     ```powershell
     .\mvnw spring-boot:run
     ```
   - En Windows (CMD):
     ```cmd
     mvnw.cmd spring-boot:run
     ```
3. La aplicación iniciará por defecto en el puerto `8080` (`http://localhost:8080`).

---

## Tabla de Endpoints

| Método | Ruta | Descripción | Códigos de respuesta |
|---|---|---|---|
| `POST` | `/api/tickets` | Crea un nuevo ticket. Siempre nace en estado `ABIERTO` y prioridad `MEDIA`. Si el cliente envía estado o prioridad, se ignoran. | `201 Created` · `400 Bad Request` |
| `GET` | `/api/tickets?estado=` | Lista los tickets ordenados por fecha de creación (más recientes primero). El parámetro `estado` es opcional (`ABIERTO`, `EN_PROCESO`, `RESUELTO`, `CERRADO`). | `200 OK` |
| `GET` | `/api/tickets/{id}` | Obtiene el detalle de un ticket por su identificador. | `200 OK` · `404 Not Found` |
| `PATCH` | `/api/tickets/{id}/estado` | Cambia el estado de un ticket según las reglas de transición. Retorna el ticket con `fechaActualizacion` actualizada. | `200 OK` · `400 Bad Request` · `404 Not Found` · `409 Conflict` |

---

## Ejemplos cURL

### 1. Crear un ticket (`POST /api/tickets`)
```bash
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "clienteNombre": "Carlos Pérez",
    "clienteContacto": "carlos@correo.com",
    "canal": "WHATSAPP",
    "mensaje": "Hola, quisiera consultar sobre el estado de mi pedido de café."
  }'
```
**Respuesta esperada (`201 Created`):**
```json
{
  "id": 1,
  "clienteNombre": "Carlos Pérez",
  "clienteContacto": "carlos@correo.com",
  "canal": "WHATSAPP",
  "mensaje": "Hola, quisiera consultar sobre el estado de mi pedido de café.",
  "estado": "ABIERTO",
  "prioridad": "MEDIA",
  "fechaCreacion": "2026-09-27T01:30:00.123456",
  "fechaActualizacion": "2026-09-27T01:30:00.123456"
}
```

### 2. Cambiar estado de un ticket (`PATCH /api/tickets/{id}/estado`)
```bash
curl -X PATCH http://localhost:8080/api/tickets/1/estado \
  -H "Content-Type: application/json" \
  -d '{
    "estado": "EN_PROCESO"
  }'
```
**Respuesta esperada (`200 OK`):**
```json
{
  "id": 1,
  "clienteNombre": "Carlos Pérez",
  "clienteContacto": "carlos@correo.com",
  "canal": "WHATSAPP",
  "mensaje": "Hola, quisiera consultar sobre el estado de mi pedido de café.",
  "estado": "EN_PROCESO",
  "prioridad": "MEDIA",
  "fechaCreacion": "2026-09-27T01:30:00.123456",
  "fechaActualizacion": "2026-09-27T01:35:12.789123"
}
```

---

## Tabla de Transiciones de Estado

Las transiciones válidas están implementadas en el enum `EstadoTicket` en el método `puedeCambiarA(EstadoTicket destino)`:

| Estado Origen | Estado Destino | Caso real |
|---|---|---|
| `ABIERTO` | `EN_PROCESO` | Alguien de soporte toma el ticket |
| `ABIERTO` | `CERRADO` | Mensaje de spam o duplicado |
| `EN_PROCESO` | `RESUELTO` | Se respondió la solución al cliente |
| `RESUELTO` | `CERRADO` | El cliente confirmó la solución |
| `RESUELTO` | `EN_PROCESO` | El cliente indica que no quedó resuelto |

> **Nota:** Cualquier otra transición es rechazada con código HTTP `409 Conflict`. `CERRADO` es un estado terminal (no permite transicionar a ningún otro estado).

---

## Formato de Error

Todas las respuestas de error siguen una estructura JSON uniforme:

```json
{
  "codigo": 400,
  "error": "Bad Request",
  "mensaje": "El campo 'mensaje' no puede estar vacío",
  "fecha": "2026-09-27T01:32:00.123456"
}
```

- `codigo`: Código numérico HTTP (`400`, `404`, `409`, `500`).
- `error`: Nombre estándar del estado HTTP (`Bad Request`, `Not Found`, `Conflict`, etc.).
- `mensaje`: Descripción detallada del error o del campo inválido.
- `fecha`: Fecha y hora en formato ISO en la que ocurrió el error.

---

## Cómo correr los tests

Las pruebas unitarias fueron construidas con **JUnit 5** y **Mockito** sin levantar el contexto de Spring.

Para ejecutar todas las pruebas:
- En Linux / macOS:
  ```bash
  ./mvnw test
  ```
- En Windows (PowerShell):
  ```powershell
  .\mvnw test
  ```
- En Windows (CMD):
  ```cmd
  mvnw.cmd test
  ```

Para ejecutar una clase de test específica:
```powershell
.\mvnw test -Dtest=EstadoTicketTest
.\mvnw test -Dtest=TicketServiceTest
```
