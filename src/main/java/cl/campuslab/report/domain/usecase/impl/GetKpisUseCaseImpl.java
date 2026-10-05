package cl.campuslab.report.domain.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cl.campuslab.report.data.exchange.bookings.BookingResponse;
import cl.campuslab.report.data.exchange.bookings.BookingsClient;
import cl.campuslab.report.domain.service.ReportRangeService;
import cl.campuslab.report.domain.usecase.GetKpisUseCase;
import cl.campuslab.report.presentation.response.KpiResponse;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetKpisUseCaseImpl implements GetKpisUseCase {

    private final BookingsClient bookingsClient;
    private final ReportRangeService rangeService;

    @Override
    public KpiResponse execute(String range) {

        LocalDate from = rangeService.getFromDate(range);
        LocalDate to = LocalDate.now();

        List<BookingResponse> bookings =
                bookingsClient.getBookings(from, to);

        int totalBookings = bookings.size();

        double bookingsPerHour =
                totalBookings / rangeService.getHours(range);

        double averageCycleTimeMinutes = bookings.stream()
                .filter(booking ->
                        booking.getStartTime() != null &&
                                booking.getEndTime() != null
                )
                .mapToLong(booking ->
                        Duration.between(
                                booking.getStartTime(),
                                booking.getEndTime()
                        ).toMinutes()
                )
                .average()
                .orElse(0);

        int occupiedResources = (int) bookings.stream()
                .filter(booking ->
                        !"CANCELLED".equals(booking.getStatus()) &&
                                !"RETURNED".equals(booking.getStatus())
                )
                .map(BookingResponse::getResourceId)
                .distinct()
                .count();

        return KpiResponse.builder()
                .totalBookings(totalBookings)
                .bookingsPerHour(bookingsPerHour)
                .averageCycleTimeMinutes(averageCycleTimeMinutes)
                .occupiedResources(occupiedResources)
                .build();
    }
}