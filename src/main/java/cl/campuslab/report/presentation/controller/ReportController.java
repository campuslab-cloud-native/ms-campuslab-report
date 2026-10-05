package cl.campuslab.report.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.campuslab.report.domain.usecase.GetKpisUseCase;
import cl.campuslab.report.domain.usecase.GetTopResourcesUseCase;
import cl.campuslab.report.presentation.response.KpiResponse;
import cl.campuslab.report.presentation.response.TopResourceResponse;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final GetKpisUseCase getKpisUseCase;
    private final GetTopResourcesUseCase getTopResourcesUseCase;

    @GetMapping("/kpis")
    public ResponseEntity<KpiResponse> getKpis(
            @RequestParam(
                    required = false,
                    defaultValue = "last24h"
            ) String range
    ) {
        return ResponseEntity.ok(
                getKpisUseCase.execute(range)
        );
    }

    @GetMapping("/top-resources")
    public ResponseEntity<List<TopResourceResponse>> getTopResources(
            @RequestParam(
                    required = false,
                    defaultValue = "last7d"
            ) String range
    ) {
        return ResponseEntity.ok(
                getTopResourcesUseCase.execute(range)
        );
    }
}