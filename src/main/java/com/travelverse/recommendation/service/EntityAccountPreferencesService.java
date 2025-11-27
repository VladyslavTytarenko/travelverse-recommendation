package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.EntityAccountPreferencesDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EntityAccountPreferencesService {

    EntityAccountPreferencesDto findLastByAccountId(@NotNull Long accountId);
}
