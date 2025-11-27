package com.travelverse.recommendation.converter;

import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import com.travelverse.recommendation.entity.EntityRecommendationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = SPRING, nullValueCheckStrategy = ALWAYS)
public abstract class EntityRecommendationHistoryConverter {

    @Mapping(target = "entityId", source = "recommendation.id")
    @Mapping(target = "categoryScore", source = "recommendation.scoreDetails.categoryScore")
    @Mapping(target = "distanceScore", source = "recommendation.scoreDetails.distanceScore")
    @Mapping(target = "qualityScore", source = "recommendation.scoreDetails.qualityScore")
    @Mapping(target = "budgetScore", source = "recommendation.scoreDetails.budgetScore")
    @Mapping(target = "crowdScore", source = "recommendation.scoreDetails.crowdScore")
    @Mapping(target = "hiddenGemScore", source = "recommendation.scoreDetails.hiddenGemScore")
    @Mapping(target = "businessScore", source = "recommendation.scoreDetails.businessScore")
    public abstract EntityRecommendationHistory toEntity(Web3EntityRecommendationDto recommendation, Long entityAccountPreferencesId);

    public List<EntityRecommendationHistory> toEntities(List<Web3EntityRecommendationDto> recommendations, Long entityAccountPreferencesId) {
        return recommendations.stream().map(recommendation -> toEntity(recommendation, entityAccountPreferencesId)).toList();
    }
}
