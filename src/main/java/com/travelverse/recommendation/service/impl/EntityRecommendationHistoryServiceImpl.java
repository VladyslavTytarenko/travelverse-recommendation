package com.travelverse.recommendation.service.impl;

import com.travelverse.recommendation.converter.EntityRecommendationHistoryConverter;
import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import com.travelverse.recommendation.repository.EntityRecommendationHistoryRepository;
import com.travelverse.recommendation.service.EntityRecommendationHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
public class EntityRecommendationHistoryServiceImpl implements EntityRecommendationHistoryService {

    private final EntityRecommendationHistoryRepository entityRecommendationHistoryRepository;
    private final EntityRecommendationHistoryConverter entityRecommendationHistoryConverter;

    @Override
    @Transactional(propagation = REQUIRES_NEW)
    public void createAll(List<Web3EntityRecommendationDto> recommendations, Long entityAccountPreferencesId) {
        Boolean isAlreadyExistsHistory = entityRecommendationHistoryRepository.existsByEntityAccountPreferencesId(entityAccountPreferencesId);
        if (FALSE.equals(isAlreadyExistsHistory)) {
            entityRecommendationHistoryRepository.saveAll(
                    entityRecommendationHistoryConverter.toEntities(recommendations, entityAccountPreferencesId));
        }
    }
}
