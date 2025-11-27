package com.travelverse.recommendation.service.impl;

import com.travelverse.recommendation.converter.EntityAccountPreferencesConverter;
import com.travelverse.recommendation.dto.EntityAccountPreferencesDto;
import com.travelverse.recommendation.entity.EntityAccountPreferences;
import com.travelverse.recommendation.repository.EntityAccountPreferencesRepository;
import com.travelverse.recommendation.service.EntityAccountPreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityAccountPreferencesServiceImpl implements EntityAccountPreferencesService {

    private final EntityAccountPreferencesRepository entityAccountPreferencesRepository;
    private final EntityAccountPreferencesConverter entityAccountPreferencesConverter;

    public EntityAccountPreferencesDto createOrUpdate(EntityAccountPreferencesDto preferences) {
        return entityAccountPreferencesConverter.toDto(
                entityAccountPreferencesRepository.save(
                        entityAccountPreferencesConverter.toEntity(preferences)));
    }

    @Override
    public EntityAccountPreferencesDto findLastByAccountId(Long accountId) {
        EntityAccountPreferences preferences = entityAccountPreferencesRepository.findFirstByAccountIdOrderByCreatedDesc(accountId)
                .orElseThrow();

        return entityAccountPreferencesConverter.toDto(preferences);
    }

    public EntityAccountPreferencesDto findById(Long id, Long accountId) {
        EntityAccountPreferences preferences = entityAccountPreferencesRepository.findByIdAndAccountId(id, accountId)
                .orElseThrow(() -> new RuntimeException("Entity account preferences history not found"));

        return entityAccountPreferencesConverter.toDto(preferences);
    }
}
