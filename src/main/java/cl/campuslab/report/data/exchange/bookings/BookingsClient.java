package cl.campuslab.report.data.exchange.bookings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class BookingsClient {

    private final RestClient restClient = RestClient.create();

    @Value("${services.bookings.url}")
    private String bookingsServiceUrl;

    public List<BookingResponse> getBookings() {
        return restClient.get()
                .uri(bookingsServiceUrl + "/api/bookings")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}