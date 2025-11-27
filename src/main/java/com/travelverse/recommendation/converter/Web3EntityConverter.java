package com.travelverse.recommendation.converter;

import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import com.travelverse.recommendation.entity.projection.Web3EntityProjection;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Map;

import static com.travelverse.recommendation.constant.Common.BUDGET;
import static com.travelverse.recommendation.constant.Common.BUSINESS;
import static com.travelverse.recommendation.constant.Common.CATEGORY;
import static com.travelverse.recommendation.constant.Common.CROWD;
import static com.travelverse.recommendation.constant.Common.DISTANCE;
import static com.travelverse.recommendation.constant.Common.HIDDEN_GEM;
import static com.travelverse.recommendation.constant.Common.QUALITY;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = SPRING, nullValueCheckStrategy = ALWAYS)
public abstract class Web3EntityConverter {


    public Web3EntityRecommendationDto toRecommendationDto(Web3EntityProjection entity,
                                                           BigDecimal totalScore,
                                                           Map<String, BigDecimal> score) {
        Web3EntityRecommendationDto.Score entityReccommendationScore = new Web3EntityRecommendationDto.Score()
                .setCategoryScore(score.get(CATEGORY))
                .setDistanceScore(score.get(DISTANCE))
                .setQualityScore(score.get(QUALITY))
                .setBudgetScore(score.get(BUDGET))
                .setCrowdScore(score.get(CROWD))
                .setHiddenGemScore(score.get(HIDDEN_GEM))
                .setBusinessScore(score.get(BUSINESS));

        return new Web3EntityRecommendationDto()
                .setId(entity.getId())
                .setCode(entity.getCode())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setAddress(entity.getAddress())
                .setObjectType(entity.getObjectType())
                .setLatitude(entity.getLatitude())
                .setLongitude(entity.getLongitude())
                .setScore(totalScore)
                .setScoreDetails(entityReccommendationScore);
    }
}
