# Notificaciones Service

Microservicio encargado de notificar a los usuarios (confirmación de compra, promociones, alertas). Antes de "enviar" cualquier mensaje, consulta a `usuario-service` para obtener el nombre y email del destinatario.

## Nota sobre el envío real

Para esta entrega, el envío se **simula con un log** (`log.info(...)` en `NotificacionService`) en vez de mandar un correo real. Esto es suficiente para demostrar el flujo completo (consulta a Usuario → "envío" → guardado en historial) sin depender de credenciales de un servidor de correo. Si más adelante quieres enviar correos de verdad, se agregaría la dependencia `spring-boot-starter-mail` y se reemplazaría esa línea por una llamada real.

## Requisitos previos

- Java 17
- Maven
- MySQL corriendo en `localhost:3306`
- `infra/eureka-server` corriendo en `http://localhost:8761`
- `usuario-service` corriendo (puerto 8081), porque este microservicio depende de él para obtener el email

## 1. Clonar el repositorio

```bash
git clone <URL_DE_TU_REPOSITORIO>
cd notificaciones-service
```

## 2. Configurar la base de datos

Abre `src/main/resources/application.properties` y reemplaza `TU_PASSWORD_AQUI`.

## 3. Compilar y ejecutar

```bash
mvn clean install
mvn spring-boot:run
```

## 4. Generar el .jar

```bash
mvn clean package
java -jar target/notificaciones-service-1.0.0.jar
```

## 5. Probar con Postman

Asegúrate de tener un usuario creado en `usuario-service` (por ejemplo id=1) antes de probar.

| Método | URL | Body (JSON) |
|---|---|---|
| POST | `http://localhost:8085/api/notificaciones` | `{"usuarioId":1,"tipo":"CONFIRMACION_COMPRA","mensaje":"Tu compra fue confirmada"}` |
| GET | `http://localhost:8085/api/notificaciones/usuario/1` | — |

Caso de error a mostrar en el video: POST con un `usuarioId` que no existe → responde `404 Not Found` (igual que en Carrito).

## Kafka (consumidor)

Este microservicio escucha el topic `compra-confirmada` (clase `messaging/CompraEventConsumer`). Cuando Carrito publica un evento, genera y guarda una notificación `CONFIRMACION_COMPRA` para ese usuario, sin que nadie llame a su endpoint.

Requiere Kafka corriendo en `localhost:9092`: desde `infra/` ejecuta `docker compose up -d`.

Para probar: confirma una compra en Carrito y luego revisa `GET http://localhost:8085/api/notificaciones/usuario/1`.
