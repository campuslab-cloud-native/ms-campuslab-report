package cl.campuslab.report.messaging;

import java.time.OffsetDateTime;

/** Evento publicado por ms-campuslab-audit en el tópico audit.timeline. */
public record AuditEvent(
        String eventId,
        Long bookingId,
        String userId,
        String type,
        OffsetDateTime timestamp,
        String source,
        String traceId,
        String correlationId
) {
}
