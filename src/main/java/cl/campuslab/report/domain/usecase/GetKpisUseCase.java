package cl.campuslab.report.domain.usecase;

import cl.campuslab.report.presentation.response.KpiResponse;

public interface GetKpisUseCase {

    KpiResponse execute(String range);
}