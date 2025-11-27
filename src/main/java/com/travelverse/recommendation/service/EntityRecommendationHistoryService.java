package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface EntityRecommendationHistoryService {

    void createAll(@NotNull List<Web3EntityRecommendationDto> recommendationHistory, @NotNull Long entityAccountPreferencesId);
}
