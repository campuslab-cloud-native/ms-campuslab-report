# ms-campuslab-report

Microservicio Spring Boot de reportería. No persiste agregados ni consume
Kafka: obtiene reservas mediante Bookings y nombres de recursos mediante
Catalog al atender las consultas.

## Endpoints internos

| Método | Ruta | Rango predeterminado |
|---|---|---|
| `GET` | `/api/v1/report/kpis` | `last24h` |
| `GET` | `/api/v1/report/top-resources` | `last7d` |

`range` acepta `last24h`, `last7d` y `last30d`. Un valor no reconocido usa la
ventana de 24 horas. Las ventanas se calculan con `America/Santiago`.

El BFF protege las rutas públicas y autoriza `ADMIN` y `AUDITOR`. Report no
implementa autenticación ni autorización HTTP propia; su API es interna.
Los métodos y respuestas están en [`openapi.yaml`](./openapi.yaml).

## Configuración

```env
SERVER_PORT=8084
BOOKINGS_SERVICE_URL=http://localhost:8081
CATALOG_SERVICE_URL=http://localhost:8082
```

## Ejecución local

Bookings y Catalog deben estar disponibles en las URLs configuradas:

```bash
./mvnw spring-boot:run
./mvnw clean package
```
