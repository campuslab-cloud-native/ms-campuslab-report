package cl.campuslab.report.domain.usecase.impl;

import cl.campuslab.report.data.exchange.bookings.BookingResponse;
import cl.campuslab.report.data.exchange.bookings.BookingsClient;
import cl.campuslab.report.data.exchange.catalog.CatalogClient;
import cl.campuslab.report.data.exchange.catalog.CatalogResourceResponse;
import cl.campuslab.report.domain.service.ReportRangeService;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportTimeZoneUseCaseTest {

    private static final Clock REPORT_CLOCK = Clock.fixed(
            Instant.parse("2026-07-18T19:00:00Z"),
            ZoneId.of("America/Santiago")
    );

    @Test
    void kpisUseSantiagoTimeForTheirRollingWindow() {
        BookingsClient bookingsClient = mock(BookingsClient.class);
        BookingResponse booking = bookingWithCreatedAt("2026-07-17T16:00:00");
        when(bookingsClient.getBookings()).thenReturn(List.of(booking));

        GetKpisUseCaseImpl useCase = new GetKpisUseCaseImpl(
                bookingsClient,
                new ReportRangeService(),
                REPORT_CLOCK
        );

        assertEquals(1, useCase.execute("last24h").getTotalBookings());
    }

    @Test
    void topResourcesUseSantiagoTimeForTheirRollingWindow() {
        BookingsClient bookingsClient = mock(BookingsClient.class);
        CatalogClient catalogClient = mock(CatalogClient.class);
        BookingResponse booking = bookingWithCreatedAt("2026-07-11T16:00:00");
        when(bookingsClient.getBookings()).thenReturn(List.of(booking));

        CatalogResourceResponse resource = new CatalogResourceResponse();
        resource.setId(7L);
        resource.setName("Resource 7");
        when(catalogClient.getResources()).thenReturn(List.of(resource));

        GetTopResourcesUseCaseImpl useCase = new GetTopResourcesUseCaseImpl(
                bookingsClient,
                catalogClient,
                new ReportRangeService(),
                REPORT_CLOCK
        );

        assertEquals(
                1,
                useCase.execute("last7d").getFirst().getTotalBookings()
        );
    }

    private BookingResponse bookingWithCreatedAt(String createdAt) {
        BookingResponse booking = new BookingResponse();
        booking.setId(1L);
        booking.setResourceId(7L);
        booking.setStatus("IN_USE");
        booking.setCreatedAt(java.time.LocalDateTime.parse(createdAt));
        return booking;
    }
}
