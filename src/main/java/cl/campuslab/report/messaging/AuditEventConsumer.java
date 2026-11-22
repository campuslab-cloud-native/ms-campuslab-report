package cl.campuslab.report.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consume audit.timeline con el grupo campuslab-report (independiente del grupo campuslab-audit).
 * Si el procesamiento falla, el manejador de errores por defecto reintenta 2 veces y continúa con el
 * siguiente mensaje, de modo que un mensaje defectuoso no bloquea la partición.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditEventConsumer {

    private final AuditEventSummary summary;

    @KafkaListener(
            topics = "${campuslab.kafka.topics.audit:audit.timeline}",
            groupId = "${spring.kafka.consumer.group-id:campuslab-report}"
    )
    public void onAuditEvent(AuditEvent event) {
        summary.record(event);
        log.debug("Audit event received. eventId={}, type={}", event.eventId(), event.type());
    }
}
