package cl.campuslab.report.domain.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportRangeService {

    public LocalDate getFromDate(String range) {

        LocalDate today = LocalDate.now();

        return switch (range) {
            case "last7d" -> today.minusDays(7);
            case "last30d" -> today.minusDays(30);
            case "last24h" -> today.minusDays(1);
            default -> today.minusDays(1);
        };
    }

    public double getHours(String range) {
        return switch (range) {
            case "last7d" -> 7 * 24.0;
            case "last30d" -> 30 * 24.0;
            default -> 24.0;
        };
    }
}