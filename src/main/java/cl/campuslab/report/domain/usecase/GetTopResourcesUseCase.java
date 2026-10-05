package cl.campuslab.report.domain.usecase;

import cl.campuslab.report.presentation.response.TopResourceResponse;

import java.util.List;

public interface GetTopResourcesUseCase {

    List<TopResourceResponse> execute(String range);
}