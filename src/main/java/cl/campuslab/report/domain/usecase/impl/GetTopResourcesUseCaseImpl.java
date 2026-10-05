package cl.campuslab.report.domain.usecase.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cl.campuslab.report.data.exchange.bookings.BookingResponse;
import cl.campuslab.report.data.exchange.bookings.BookingsClient;
import cl.campuslab.report.data.exchange.catalog.CatalogClient;
import cl.campuslab.report.data.exchange.catalog.CatalogResourceResponse;
import cl.campuslab.report.domain.service.ReportRangeService;
import cl.campuslab.report.domain.usecase.GetTopResourcesUseCase;
import cl.campuslab.report.presentation.response.TopResourceResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetTopResourcesUseCaseImpl
        implements GetTopResourcesUseCase {

    private final BookingsClient bookingsClient;
    private final CatalogClient catalogClient;
    private final ReportRangeService rangeService;

    @Override
    public List<TopResourceResponse> execute(String range) {

        LocalDate from = rangeService.getFromDate(range);
        LocalDate to = LocalDate.now();

        List<BookingResponse> bookings =
                bookingsClient.getBookings(from, to);

        List<CatalogResourceResponse> resources =
                catalogClient.getResources();

        Map<Long, CatalogResourceResponse> resourcesById =
                resources.stream()
                        .collect(Collectors.toMap(
                                CatalogResourceResponse::getId,
                                Function.identity()
                        ));

        Map<Long, Long> bookingsByResource =
                bookings.stream()
                        .collect(Collectors.groupingBy(
                                BookingResponse::getResourceId,
                                Collectors.counting()
                        ));

        return bookingsByResource.entrySet()
                .stream()
                .map(entry -> {

                    CatalogResourceResponse resource =
                            resourcesById.get(entry.getKey());

                    return TopResourceResponse.builder()
                            .resourceId(entry.getKey())
                            .resourceName(
                                    resource != null
                                            ? resource.getName()
                                            : "Recurso " + entry.getKey()
                            )
                            .totalBookings(entry.getValue().intValue())
                            .build();
                })
                .sorted((a, b) ->
                        b.getTotalBookings()
                                .compareTo(a.getTotalBookings())
                )
                .toList();
    }
}