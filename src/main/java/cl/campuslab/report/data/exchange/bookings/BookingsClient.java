package report.data.exchange.bookings;

import cl.campuslab.report.data.exchange.bookings.BookingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingsClient {

    private final RestClient restClient = RestClient.create();

    @Value("${services.bookings.url}")
    private String bookingsServiceUrl;

    public List<BookingResponse> getBookings(
            LocalDate from,
            LocalDate to
    ) {

        var uri = UriComponentsBuilder
                .fromUriString(bookingsServiceUrl)
                .path("/api/bookings")
                .queryParam("from", from)
                .queryParam("to", to)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}