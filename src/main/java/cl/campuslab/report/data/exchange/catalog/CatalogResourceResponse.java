package cl.campuslab.report.data.exchange.catalog;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CatalogResourceResponse {

    private Long id;
    private String name;
    private String type;
    private Integer availableQuantity;
}