package cl.campuslab.report.data.exchange.catalog;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class CatalogClient {

    private final RestClient restClient = RestClient.create();

    @Value("${services.catalog.url}")
    private String catalogServiceUrl;

    public List<CatalogResourceResponse> getResources() {

        var uri = UriComponentsBuilder
                .fromUriString(catalogServiceUrl)
                .path("/api/catalog/resources")
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}