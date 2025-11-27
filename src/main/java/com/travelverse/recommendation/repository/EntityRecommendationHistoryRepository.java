package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.EntityRecommendationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRecommendationHistoryRepository extends JpaRepository<EntityRecommendationHistory, Long> {

    Boolean existsByEntityAccountPreferencesId(Long entityAccountPreferencesId);
}
