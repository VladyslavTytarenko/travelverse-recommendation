package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface Web3EntityRecommendationService {

    List<Web3EntityRecommendationDto> recommendAndCreate(@NotNull Long accountId);
}
