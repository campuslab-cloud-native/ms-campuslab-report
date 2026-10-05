package cl.campuslab.report.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiResponse {

    private Integer totalBookings;
    private Double bookingsPerHour;
    private Double averageCycleTimeMinutes;
    private Integer occupiedResources;
}
