package cl.campuslab.report.domain.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportRangeServiceTest {

    private final ReportRangeService service = new ReportRangeService();
    private final LocalDateTime to = LocalDateTime.of(2026, 10, 16, 1, 0);

    @Test
    void calculatesRollingWindows() {
        assertEquals(to.minusHours(24), service.getFromDateTime("last24h", to));
        assertEquals(to.minusDays(7), service.getFromDateTime("last7d", to));
        assertEquals(to.minusDays(30), service.getFromDateTime("last30d", to));
    }

    @Test
    void includesCreatedAtAtBothRangeBoundaries() {
        LocalDateTime from = to.minusHours(24);

        assertTrue(service.includesCreatedAt(from, "last24h", to));
        assertTrue(service.includesCreatedAt(to, "last24h", to));
        assertFalse(service.includesCreatedAt(from.minusNanos(1), "last24h", to));
        assertFalse(service.includesCreatedAt(null, "last24h", to));
    }
}
