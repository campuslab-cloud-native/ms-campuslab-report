# ms-campuslab-report

Microservicio de reportería y KPIs de CampusLab.

## Tecnologías

- Java
- Spring Boot
- Spring Data JPA
- Oracle
- Kafka

## Funcionalidades

- Consultar KPIs.
- Consultar recursos más utilizados.
- Procesar eventos de reservas desde Kafka.
- Mantener información agregada para reportería.

## Endpoints

```http
GET /api/report/kpis?range=last24h
GET /api/report/top-resources?range=last7d
```

## Acceso

Solo lectura.

## Rol autorizado

```text
ADMIN
```

## Kafka

Consume:

```text
bookings.events
```

## Base de datos

Oracle.

## Variables de entorno

```env
DB_URL=
DB_USERNAME=
DB_PASSWORD=
KAFKA_BOOTSTRAP_SERVERS=
```

## Ejecución local

```bash
./mvnw spring-boot:run
```

## Build

```bash
./mvnw clean package
```
