# ☕ Atiende - Sistema de Gestión de Tickets (Grano Alto Café)

## 📌 Sobre el Proyecto

**Atiende** es un proyecto de aprendizaje enfocado en el desarrollo backend con **Spring Boot** para la centralización y gestión de tickets de soporte técnico multicanal (WhatsApp, correo y web).

> 🤖 **Enfoque de Aprendizaje e IA:** Desarrollado como un laboratorio práctico para aprender y evaluar flujos de trabajo de **desarrollo asistido por IA (Cursor AI / AI-Driven Development)**, enfocándose en la aceleración del ciclo de software, diseño de APIs REST y generación de pruebas unitarias.

---

## 🛠️ Aspectos Técnicos

- **Stack:** Java 17, Spring Boot 3, MySQL.
- **Máquina de Estados en `Enum`:** Reglas de negocio explícitas para la transición de estados (`ABIERTO` $\rightarrow$ `EN_PROCESO` $\rightarrow$ `RESUELTO` $\rightarrow$ `CERRADO`).
- **Manejo de Errores:** Excepciones globales estandarizadas (`@ControllerAdvice`).
- **Testing:** Pruebas unitarias aisladas con **JUnit 5** y **Mockito**.

---

## 🔌 Endpoints de la API

- `POST /api/tickets` - Creación de tickets.
- `GET /api/tickets` - Listado y filtrado por estado.
- `GET /api/tickets/{id}` - Consulta de detalle.
- `PATCH /api/tickets/{id}/estado` - Cambio de estado según reglas de negocio.