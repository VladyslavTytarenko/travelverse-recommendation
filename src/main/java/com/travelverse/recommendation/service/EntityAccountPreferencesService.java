package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.EntityAccountPreferencesDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface EntityAccountPreferencesService {

    EntityAccountPreferencesDto findById(@NotNull Long id, @NotNull Long accountId);

    EntityAccountPreferencesDto createOrUpdate(@NotNull EntityAccountPreferencesDto preferences);

    EntityAccountPreferencesDto findLastByAccountId(@NotNull Long accountId);
}
