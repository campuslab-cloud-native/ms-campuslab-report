package cl.campuslab.report.presentation.response;

import java.time.OffsetDateTime;
import java.util.Map;

public record EventSummaryResponse(
        long total,
        Map<String, Long> byType,
        OffsetDateTime lastEventAt
) {
}
