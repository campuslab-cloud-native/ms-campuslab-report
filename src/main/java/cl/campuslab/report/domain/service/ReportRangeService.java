package cl.campuslab.report.domain.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportRangeService {

    public LocalDateTime getFromDateTime(
            String range,
            LocalDateTime to
    ) {
        return switch (range) {
            case "last7d" -> to.minusDays(7);
            case "last30d" -> to.minusDays(30);
            case "last24h" -> to.minusHours(24);
            default -> to.minusHours(24);
        };
    }

    public boolean includesCreatedAt(
            LocalDateTime createdAt,
            String range,
            LocalDateTime to
    ) {
        if (createdAt == null) {
            return false;
        }

        LocalDateTime from = getFromDateTime(range, to);
        return !createdAt.isBefore(from) && !createdAt.isAfter(to);
    }

    public double getHours(String range) {
        return switch (range) {
            case "last7d" -> 7 * 24.0;
            case "last30d" -> 30 * 24.0;
            default -> 24.0;
        };
    }
}