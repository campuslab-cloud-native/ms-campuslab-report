package cl.campuslab.report.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopResourceResponse {

    private Long resourceId;
    private String resourceName;
    private Integer totalBookings;
}
