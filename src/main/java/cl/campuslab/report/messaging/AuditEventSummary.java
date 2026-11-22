package cl.campuslab.report.messaging;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

import org.springframework.stereotype.Component;

/**
 * Resumen en memoria de los eventos recibidos desde audit.timeline.
 * Se reinicia al reiniciar el servicio; no reemplaza a los KPIs calculados desde bookings.
 */
@Component
public class AuditEventSummary {

    private final Map<String, LongAdder> countsByType = new ConcurrentHashMap<>();
    private final LongAdder total = new LongAdder();
    private final AtomicReference<OffsetDateTime> lastEventAt = new AtomicReference<>();

    public void record(AuditEvent event) {
        String type = event.type() == null ? "UNKNOWN" : event.type();
        countsByType.computeIfAbsent(type, key -> new LongAdder()).increment();
        total.increment();
        if (event.timestamp() != null) {
            lastEventAt.accumulateAndGet(event.timestamp(),
                    (current, candidate) -> current == null || candidate.isAfter(current) ? candidate : current);
        }
    }

    public Snapshot snapshot() {
        Map<String, Long> byType = new TreeMap<>();
        countsByType.forEach((type, count) -> byType.put(type, count.sum()));
        return new Snapshot(total.sum(), byType, lastEventAt.get());
    }

    public record Snapshot(long total, Map<String, Long> byType, OffsetDateTime lastEventAt) {
    }
}
