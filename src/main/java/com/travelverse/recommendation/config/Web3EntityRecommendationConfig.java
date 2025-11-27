package com.travelverse.recommendation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class Web3EntityRecommendationConfig {

    @Value("${entity-recommendation.resultsLimit}")
    private int resultsLimit;

    @Value("${entity-recommendation.excludedRatingTo}")
    private double excludedRatingTo;

    @Value("${entity-recommendation.category.relatedScore}")
    private double categoryRelatedScore;

    @Value("${entity-recommendation.weights.category}")
    private double categoryWeight;

    @Value("${entity-recommendation.weights.crowd}")
    private double crowdWeight;

    @Value("${entity-recommendation.weights.distance}")
    private double distanceWeight;

    @Value("${entity-recommendation.weights.quality}")
    private double qualityWeight;

    @Value("${entity-recommendation.weights.budget}")
    private double budgetWeight;

    @Value("${entity-recommendation.weights.hiddenGem}")
    private double hiddenGemWeight;

    @Value("${entity-recommendation.weights.businessRules}")
    private double businessRulesWeight;

    @Value("${entity-recommendation.searchRadiusInKm}")
    private int searchRadiusInKm;

    @Value("${entity-recommendation.distance.walking}")
    private int walkingDistanceInKm;

    @Value("${entity-recommendation.distance.biking}")
    private int bikingDistanceInKm;

    @Value("${entity-recommendation.distance.publicTransport}")
    private int publicTransportDistanceInKm;

    @Value("${entity-recommendation.distance.car}")
    private int carDistanceInKm;

    @Value("${entity-recommendation.rating.base}")
    private double ratingBase;

    @Value("${entity-recommendation.rating.scale}")
    private double ratingScale;

    @Value("${entity-recommendation.rating.weight.rating}")
    private double ratingWeightRating;

    @Value("${entity-recommendation.rating.weight.reviews}")
    private double ratingWeightReviews;

    @Value("${entity-recommendation.rating.weight.expert}")
    private double ratingWeightExpert;

    @Value("${entity-recommendation.traffic.cap}")
    private double trafficCap;

    @Value("${entity-recommendation.traffic.default}")
    private double defaultTraffic;

    @Value("${entity-recommendation.traffic.neutralWeight}")
    private double trafficNeutralWeight;

    @Value("${entity-recommendation.traffic.defaultScore}")
    private int trafficDefaultScore;
}
