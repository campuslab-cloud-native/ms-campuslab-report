# Changelog

Todos los cambios relevantes de este repositorio se documentan aquí.
El formato sigue [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/) y el versionamiento sigue [SemVer](https://semver.org/lang/es/).

## [Sin publicar]

- Consumidor de `audit.timeline` (grupo `campuslab-report`) con resumen en memoria y endpoint `GET /api/v1/report/events/summary`.
- Variable `REPORT_KAFKA_ENABLED` para desactivar el consumidor.

## [1.0.0] - 2026-11-22

Primera versión estable.

- KPIs de reservas por hora, tiempo de ciclo y recursos más usados.
- API versionada en `/api/v1/report`.
