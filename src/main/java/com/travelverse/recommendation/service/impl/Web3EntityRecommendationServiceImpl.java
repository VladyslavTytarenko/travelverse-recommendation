package com.travelverse.recommendation.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelverse.recommendation.config.Web3EntityRecommendationConfig;
import com.travelverse.recommendation.converter.Web3EntityConverter;
import com.travelverse.recommendation.dto.EntityAccountPreferencesDto;
import com.travelverse.recommendation.dto.EntityExternalDataDto;
import com.travelverse.recommendation.dto.VenueSearchResponseDto;
import com.travelverse.recommendation.dto.Web3EntityRecommendationCriteria;
import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import com.travelverse.recommendation.dto.WeeklyTrafficStatsDto;
import com.travelverse.recommendation.entity.projection.Web3EntityProjection;
import com.travelverse.recommendation.repository.Web3EntityRepository;
import com.travelverse.recommendation.service.CategoryService;
import com.travelverse.recommendation.service.EntityAccountPreferencesService;
import com.travelverse.recommendation.service.EntityExternalDataService;
import com.travelverse.recommendation.service.EntityRecommendationHistoryService;
import com.travelverse.recommendation.service.Web3EntityRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.travelverse.recommendation.constant.Common.*;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.clamp;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.log1p;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.collections4.MapUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class Web3EntityRecommendationServiceImpl implements Web3EntityRecommendationService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final EntityExternalDataService entityExternalDataService;
    private final Web3EntityRepository web3EntityRepository;
    private final Web3EntityRecommendationConfig config;
    private final CategoryService categoryService;
    private final EntityRecommendationHistoryService entityRecommendationHistoryService;
    private final EntityAccountPreferencesService entityAccountPreferencesService;
    private final Web3EntityConverter web3EntityConverter;

    @Override
    public List<Web3EntityRecommendationDto> recommendAndCreate(Long accountId) {
        EntityAccountPreferencesDto accountPreferences = entityAccountPreferencesService.findLastByAccountId(accountId);
        Map<String, Object> preferences = accountPreferences.getPreferences();
        Web3EntityRecommendationCriteria criteria =
                objectMapper.convertValue(preferences, Web3EntityRecommendationCriteria.class);

        boolean isDisableRecommendation = isNull(criteria.getSearchLatitude()) && isNull(criteria.getSearchLongitude())
                && isNull(criteria.getCurrentLatitude()) && isNull(criteria.getCurrentLongitude());

        List<Web3EntityRecommendationDto> recommendations = isDisableRecommendation ? emptyList() : recommend(criteria);

        runAsync(() -> entityRecommendationHistoryService.createAll(recommendations, accountPreferences.getId()));

        return recommendations;
    }

    private List<Web3EntityRecommendationDto> recommend(Web3EntityRecommendationCriteria criteria) {
        boolean disableDistanceScore = isDisableDistanceScore(criteria);
        double latitudeForSearch = disableDistanceScore ? criteria.getSearchLatitude().doubleValue() : criteria.getCurrentLatitude().doubleValue();
        double longitudeForSearch = disableDistanceScore ? criteria.getSearchLongitude().doubleValue() : criteria.getCurrentLongitude().doubleValue();

        if (isEmpty(criteria.getExcludedEntityTypeIds())) criteria.setExcludedEntityTypeIds(null);
        List<Web3EntityProjection> candidates = web3EntityRepository.findWithinRadius(
                latitudeForSearch,
                longitudeForSearch,
                config.getSearchRadiusInKm(),
                criteria.getLocale(),
                criteria.getExcludedEntityTypeIds());

        double latitude = floor(latitudeForSearch * 100) / 100;
        double longitude = floor(longitudeForSearch * 100) / 100;

        final Map<Long, EntityExternalDataDto> entityIdToEntityExternalData =
                (nonNull(criteria.getSearchLatitude()) && nonNull(criteria.getSearchLongitude()))
                        ? findEntityExternalData(criteria, candidates)
                        : emptyMap();

        final Map<String, VenueSearchResponseDto.VenueDto> venueLiveData =
                (nonNull(criteria.getSearchLatitude()) && nonNull(criteria.getSearchLongitude()))
                        ? emptyMap()
                        : entityExternalDataService.findVenueLiveData(latitude, longitude);

        Set<Long> entityTypeIds = categoryService.findAllEntityTypeIdsFromSubcategoryByEntityTypeIds(criteria.getEntityTypeIds());

        int resultLimit = config.getResultsLimit();
        int offset = (max(criteria.getAttempt(), 1) - 1) * resultLimit;

        boolean hasBusinessScore = candidates.stream()
                .anyMatch(entity -> nonNull(entity.getBusinessScore()));

        Map<String, BigDecimal> normalizedWeights = normalizeWeights(criteria, disableDistanceScore, hasBusinessScore);

        BigDecimal scoreThreshold = valueOf(config.getExcludedRatingTo()).multiply(valueOf(ONE_STAR_VALUE));

        List<Web3EntityRecommendationDto> sorted = candidates.stream()
                .map(entity -> mapToRecommendationDto(entity, criteria, venueLiveData, entityIdToEntityExternalData,
                        entityTypeIds, criteria.getEntityTypeIds(), normalizedWeights,
                        disableDistanceScore, hasBusinessScore))
                .filter(dto -> dto.getScore().compareTo(scoreThreshold) >= ZERO.intValue())
                .sorted(comparing(Web3EntityRecommendationDto::getScore).reversed())
                .toList();

        return sorted.stream()
                .skip(offset)
                .limit(resultLimit)
                .toList();
    }

    private Map<Long, EntityExternalDataDto> findEntityExternalData(Web3EntityRecommendationCriteria criteria, List<Web3EntityProjection> candidates) {
        if (nonNull(criteria.getSearchLatitude()) && nonNull(criteria.getSearchLongitude())) {
            Set<String> venueIds = candidates.stream().map(Web3EntityProjection::getVenueId).collect(toSet());
            List<EntityExternalDataDto> entityExternalData = entityExternalDataService.findForcastByVenueIds(venueIds);
            return entityExternalData.stream()
                    .collect(toMap(EntityExternalDataDto::getEntityId, identity()));
        } else return emptyMap();
    }

    private Web3EntityRecommendationDto mapToRecommendationDto(Web3EntityProjection entity,
                                                               Web3EntityRecommendationCriteria criteria,
                                                               Map<String, VenueSearchResponseDto.VenueDto> venueLiveData,
                                                               Map<Long, EntityExternalDataDto> entityIdToEntityExternalData,
                                                               Set<Long> entityTypeIds,
                                                               Set<Long> selectedEntityTypeIds,
                                                               Map<String, BigDecimal> normalizedWeights,
                                                               boolean disableDistanceScore,
                                                               boolean hasBusinessScore) {
        double distance = disableDistanceScore ? ZERO.doubleValue() : haversineDistance(
                criteria.getCurrentLatitude().doubleValue(),
                criteria.getCurrentLongitude().doubleValue(),
                entity.getLatitude(),
                entity.getLongitude());

        VenueSearchResponseDto.VenueDto venue = venueLiveData.get(entity.getVenueId());
        TrafficInfo trafficInfo = extractTrafficInfo(venue);

        BigDecimal combinedTraffic = disableDistanceScore && nonNull(criteria.getSearchLongitude()) && nonNull(criteria.getSearchLatitude()) ?
                computeCombinedTraffic(entityIdToEntityExternalData.get(entity.getId())) :
                computeCombinedTraffic(trafficInfo.currentTraffic, trafficInfo.dayMax, trafficInfo.dayRankMax);

        BigDecimal categoryScore = computeCategoryScore(entity.getObjectTypeId(), entityTypeIds, selectedEntityTypeIds);
        BigDecimal distanceScore = disableDistanceScore ? ZERO : computeDistanceScore(distance, criteria.getTravelMode());
        BigDecimal qualityScore = computeQualityScore(entity);
        BigDecimal budgetScore = computeBudgetScore(entity.getPriceLevel(), criteria.getBudgetPreference());
        BigDecimal crowdScore = computeCrowdScore(combinedTraffic, criteria.getCrowdPreference());
        BigDecimal hiddenGemScore = computeHiddenGemScore(criteria.getHiddenGemPreference(), entity.getIsHiddenGem());
        BigDecimal businessScore = hasBusinessScore ? computeBusinessScore(entity.getBusinessScore()) : ZERO;

        BigDecimal totalScore = categoryScore.multiply(normalizedWeights.get(CATEGORY))
                .add(distanceScore.multiply(normalizedWeights.get(DISTANCE)))
                .add(qualityScore.multiply(normalizedWeights.get(QUALITY)))
                .add(budgetScore.multiply(normalizedWeights.get(BUDGET)))
                .add(crowdScore.multiply(normalizedWeights.get(CROWD)))
                .add(hiddenGemScore.multiply(normalizedWeights.get(HIDDEN_GEM)))
                .add(businessScore.multiply(normalizedWeights.get(BUSINESS)))
                .setScale(2, HALF_UP);

        Map<String, BigDecimal> score = new HashMap<>();
        score.put(TOTAL_SCORE, totalScore);
        score.put(CATEGORY, isEmpty(criteria.getEntityTypeIds()) ? null : categoryScore);
        score.put(DISTANCE, disableDistanceScore ? null : distanceScore);
        score.put(QUALITY, qualityScore);
        score.put(BUDGET, nonNull(criteria.getBudgetPreference()) ? budgetScore : null);
        score.put(CROWD, crowdScore);
        score.put(HIDDEN_GEM, hiddenGemScore);
        score.put(BUSINESS, hasBusinessScore ? businessScore : null);

        return web3EntityConverter.toRecommendationDto(entity, totalScore, score);
    }

    private TrafficInfo extractTrafficInfo(VenueSearchResponseDto.VenueDto venue) {
        int currentTraffic = 0;
        int dayMax = 0;
        int dayRankMax = 7;

        if (nonNull(venue)) {
            if (isNotEmpty(venue.getDayRaw())) currentTraffic = venue.getDayRaw().get(0);
            if (nonNull(venue.getDayInfo())) {
                dayMax = venue.getDayInfo().getDayMax();
                dayRankMax = venue.getDayInfo().getDayRankMax();
            }
        }

        return new TrafficInfo(currentTraffic, dayMax, dayRankMax);
    }

    private record TrafficInfo(int currentTraffic, int dayMax, int dayRankMax) {}

    private BigDecimal computeDistanceScore(double distanceKm, String travelMode) {
        double optimalRadius = switch (travelMode) {
            case WALK -> config.getWalkingDistanceInKm();
            case BIKE -> config.getBikingDistanceInKm();
            case PUBLIC_TRANSPORT -> config.getPublicTransportDistanceInKm();
            case CAR -> config.getCarDistanceInKm();
            default -> DEFAULT_RADIUS_IN_KM;
        };

        BigDecimal rawPenalty = valueOf(100 * Math.exp(-distanceKm / optimalRadius));

        return rawPenalty.setScale(2, HALF_UP);
    }

    private BigDecimal computeCategoryScore(Long poiTypeId, Set<Long> entityTypeIds, Set<Long> selectedEntityTypeIds) {
        if (isEmpty(entityTypeIds) || isEmpty(selectedEntityTypeIds)) return ZERO;
        if (selectedEntityTypeIds.contains(poiTypeId)) return valueOf(ONE_HUNDRED);
        if (entityTypeIds.contains(poiTypeId)) return valueOf(config.getCategoryRelatedScore());

        return ZERO;
    }

    private double haversineDistance(double accountLatitude, double accountLongitude, double entityLatitude, double entityLongitude) {
        double deltaLatitude = toRadians(entityLatitude - accountLatitude);
        double deltaLongitude = toRadians(entityLongitude - accountLongitude);

        double accountLatitudeRad = toRadians(accountLatitude);
        double entityLatitudeRad = toRadians(entityLatitude);

        double haversine =
                sin(deltaLatitude / 2) * sin(deltaLatitude / 2)
                        + cos(accountLatitudeRad) * cos(entityLatitudeRad)
                        * sin(deltaLongitude / 2) * sin(deltaLongitude / 2);

        double centralAngle = 2 * atan2(sqrt(haversine), sqrt(1 - haversine));

        return EARTH_RADIUS_KM * centralAngle;
    }

    private BigDecimal computeQualityScore(Web3EntityProjection poi) {
        BigDecimal normalizedRating = ofNullable(poi.getRating())
                .orElse(valueOf(config.getRatingBase()))
                .divide(MAX_RATING, 6, HALF_UP)
                .max(ZERO).min(ONE);

        BigDecimal normalizedReviews = ofNullable(poi.getReviews())
                .filter(reviews -> reviews > ZERO.intValue())
                .map(reviews -> {
                    double ratio = log1p(reviews) / log(1000);
                    return valueOf(ratio).max(ZERO).min(ONE);
                })
                .orElse(ZERO);

        BigDecimal expertScore = ofNullable(poi.getExpertRating())
                .map(er -> er.divide(MAX_RATING, 6, HALF_UP))
                .orElse(ZERO)
                .max(ZERO).min(ONE);

        BigDecimal score =
                normalizedRating.multiply(valueOf(config.getRatingWeightRating()))
                        .add(normalizedReviews.multiply(valueOf(config.getRatingWeightReviews())))
                        .add(expertScore.multiply(valueOf(config.getRatingWeightExpert())));

        return score.multiply(valueOf(ONE_HUNDRED)).setScale(2, HALF_UP);
    }

    public BigDecimal computeCombinedTraffic(int currentTrafficPercent,
                                             int dayMaxPercent,
                                             int dayRankMax) {
        if (currentTrafficPercent == ZERO.intValue() && dayMaxPercent == ZERO.intValue()) return ZERO;

        BigDecimal currentTraffic = normalizeTraffic(currentTrafficPercent);
        BigDecimal dailyPeak = normalizeTraffic(dayMaxPercent);
        BigDecimal headroom = ONE.subtract(dailyPeak).max(ZERO);
        BigDecimal spikeProbability = computeSpikeProbability(dayRankMax);
        BigDecimal expectedIncrease = headroom.multiply(spikeProbability);

        BigDecimal combinedTraffic = currentTraffic.add(expectedIncrease).min(valueOf(config.getTrafficCap()));

        return combinedTraffic.setScale(4, HALF_UP);
    }

    private BigDecimal computeCombinedTraffic(EntityExternalDataDto entityExternalData) {
        WeeklyTrafficStatsDto stats = buildWeeklyStats(entityExternalData);

        if (isNull(stats)) return ZERO;

        BigDecimal peak = nonNull(stats.getWeeklyPeak()) ? stats.getWeeklyPeak() : DEFAULT_TRAFFIC;

        BigDecimal potentialGrowth = HUNDRED.subtract(peak).max(ZERO);

        BigDecimal quietRatio = valueOf(stats.getQuietHours()).divide(HOURS_IN_WEEK, 6, HALF_UP);
        BigDecimal peakRatio = valueOf(stats.getPeakHours()).divide(HOURS_IN_WEEK, 6, HALF_UP);
        BigDecimal quietPeakBalance = quietRatio.subtract(peakRatio).max(ZERO);

        BigDecimal adjustedTrafficIncrease = potentialGrowth.multiply(ONE.subtract(quietPeakBalance));

        BigDecimal mean = nonNull(stats.getWeeklyMean()) ? stats.getWeeklyMean() : DEFAULT_TRAFFIC;

        BigDecimal trafficPercent = mean.add(adjustedTrafficIncrease);

        return trafficPercent.divide(HUNDRED, 4, HALF_UP);
    }

    public WeeklyTrafficStatsDto buildWeeklyStats(EntityExternalDataDto dto) {
        if (isNull(dto)) return null;

        int daysCount = ZERO.intValue();
        BigDecimal meanSum = ZERO;
        BigDecimal peakMax = ZERO;
        int quietSum = ZERO.intValue();
        int peakSum = ZERO.intValue();

        for (Map<String, Object> day : getWeek(dto)) {
            if (isNull(day) || isEmpty(day)) continue;

            Map<String, Object> dayInfo = (Map<String, Object>) day.get(DAY_INFO);

            daysCount++;
            meanSum = meanSum.add(getDayMean(dayInfo));
            peakMax = peakMax.max(getDayMax(dayInfo));
            quietSum += getHourCount(day, QUIET_HOURS);
            peakSum += getHourCount(day, PEAK_HOURS);
        }

        if (daysCount == ZERO.intValue()) daysCount = ONE.intValue();

        return new WeeklyTrafficStatsDto()
                .setWeeklyMean(meanSum.divide(valueOf(daysCount), 6, HALF_UP))
                .setWeeklyPeak(peakMax)
                .setQuietHours(quietSum)
                .setPeakHours(peakSum);
    }

    private List<Map<String, Object>> getWeek(EntityExternalDataDto dto) {
        if (isNull(dto)) return emptyList();
        return List.of(
                safeMap(dto.getMonday()),
                safeMap(dto.getTuesday()),
                safeMap(dto.getWednesday()),
                safeMap(dto.getThursday()),
                safeMap(dto.getFriday()),
                safeMap(dto.getSaturday()),
                safeMap(dto.getSunday()));
    }

    private Map<String, Object> safeMap(Map<String, Object> day) {
        return nonNull(day) ? day : emptyMap();
    }

    private BigDecimal getDayMean(Map<String, Object> dayInfo) {
        if (isNull(dayInfo)) return ZERO;

        Number mean = (Number) dayInfo.get(DAY_MEAN);
        return nonNull(mean) ? valueOf(mean.doubleValue()) : ZERO;
    }

    private BigDecimal getDayMax(Map<String, Object> dayInfo) {
        if (isNull(dayInfo)) return ZERO;

        Number max = (Number) dayInfo.get(DAY_MAX);
        return nonNull(max) ? valueOf(max.doubleValue()) : ZERO;
    }

    private int getHourCount(Map<String, Object> day, String key) {
        if (day.get(key) instanceof Iterable<?> iterable) {
            long size = iterable.spliterator().getExactSizeIfKnown();
            return (int) max(size, ZERO.intValue());
        }
        return ZERO.intValue();
    }

    private BigDecimal normalizeTraffic(int percent) {
        double capped = min(percent, config.getTrafficCap() * 100);
        return valueOf(capped)
                .divide(HUNDRED, 6, HALF_UP);
    }

    private BigDecimal computeSpikeProbability(int rankMax) {
        int clampedRank = clamp(rankMax, 1, 7);
        double spike = (8.0 - clampedRank) / 7.0;

        return valueOf(spike).setScale(6, HALF_UP);
    }

    public BigDecimal computeCrowdScore(BigDecimal combinedTraffic, Integer crowdPreference) {
        BigDecimal traffic = nonNull(combinedTraffic) ? combinedTraffic : valueOf(config.getDefaultTraffic());
        int preference = nonNull(crowdPreference) ? crowdPreference : NEUTRAL_CROWD;

        BigDecimal score = switch (preference) {
            case AVOID_CROWD -> ONE.subtract(traffic).multiply(HUNDRED);
            case LIKE_CROWD -> traffic.multiply(HUNDRED);
            case NEUTRAL_CROWD -> ONE.subtract(traffic.multiply(valueOf(config.getTrafficNeutralWeight())))
                    .multiply(HUNDRED);
            default -> valueOf(config.getTrafficDefaultScore());
        };

        double clampedScore = clamp(score.doubleValue(), 0, 100);

        return valueOf(clampedScore).setScale(2, HALF_UP);
    }

    private BigDecimal computeBudgetScore(Integer entityPriceLevel, Integer userBudgetPreference) {
        if (isNull(entityPriceLevel) || isNull(userBudgetPreference)
                || entityPriceLevel < 1 || entityPriceLevel > 5
                || userBudgetPreference < 1 || userBudgetPreference > 5) {
            return valueOf(50);
        }

        int entityBudgetCategory = getBudgetCategory(entityPriceLevel);
        int budgetPreferenceCategory = getBudgetCategory(userBudgetPreference);

        if (entityBudgetCategory == budgetPreferenceCategory) {
            if (Objects.equals(entityPriceLevel, userBudgetPreference)) return HUNDRED;
            return valueOf(75);
        }

        int diff = abs(entityPriceLevel - userBudgetPreference);
        if (diff == 1) return valueOf(25);
        return ZERO;
    }

    private int getBudgetCategory(int level) {
        if (level == 1) return 1;
        if (level <= 3) return 2;
        return 3;
    }

    private BigDecimal computeHiddenGemScore(Integer hiddenGemPreference, boolean isHiddenGem) {
        if (isNull(hiddenGemPreference)) return valueOf(50);

        return switch (hiddenGemPreference) {
            case CALM_AND_QUIET -> isHiddenGem ? HUNDRED : ZERO;
            case DEPENDS_ON_VIBE -> valueOf(50);
            case LIVELY -> isHiddenGem ? ZERO : HUNDRED;
            default -> ZERO;
        };
    }

    private BigDecimal computeBusinessScore(Integer businessRuleValue) {
        if (isNull(businessRuleValue)) return ZERO;
        return valueOf(clamp(businessRuleValue, 0, 100));
    }

    private Map<String, BigDecimal> normalizeWeights(Web3EntityRecommendationCriteria criteria, boolean disableDistanceScore, boolean hasBusinessScore) {
        Map<String, BigDecimal> weights = new HashMap<>();
        weights.put(CATEGORY, valueOf(config.getCategoryWeight()));
        weights.put(DISTANCE, valueOf(config.getDistanceWeight()));
        weights.put(QUALITY, valueOf(config.getQualityWeight()));
        weights.put(BUDGET, valueOf(config.getBudgetWeight()));
        weights.put(CROWD, valueOf(config.getCrowdWeight()));
        weights.put(HIDDEN_GEM, valueOf(config.getHiddenGemWeight()));
        weights.put(BUSINESS, valueOf(config.getBusinessRulesWeight()));

        if (isEmpty(criteria.getEntityTypeIds())) weights.put(CATEGORY, ZERO);
        if (disableDistanceScore || isNull(criteria.getTravelMode())) weights.put(DISTANCE, ZERO);
        if (isNull(criteria.getBudgetPreference())) weights.put(BUDGET, ZERO);
        if (!hasBusinessScore) weights.put(BUSINESS, ZERO);


        BigDecimal sumOfActiveWeights = weights.values().stream().reduce(ZERO, BigDecimal::add);

        Map<String, BigDecimal> normalizedWeights = new HashMap<>();
        weights.forEach((weightName, weightValue) -> {
            if (weightValue.compareTo(ZERO) > 0) {
                BigDecimal normalized = weightValue.divide(sumOfActiveWeights, 6, HALF_UP);
                normalizedWeights.put(weightName, normalized);
            } else {
                normalizedWeights.put(weightName, ZERO);
            }
        });

        return normalizedWeights;
    }

    private boolean isDisableDistanceScore(Web3EntityRecommendationCriteria criteria) {
        BigDecimal searchLat = nonNull(criteria.getSearchLatitude()) ? criteria.getSearchLatitude() : ZERO;
        BigDecimal searchLon = nonNull(criteria.getSearchLongitude()) ? criteria.getSearchLongitude() : ZERO;
        BigDecimal currentLat = nonNull(criteria.getCurrentLatitude()) ? criteria.getCurrentLatitude() : ZERO;
        BigDecimal currentLon = nonNull(criteria.getCurrentLongitude()) ? criteria.getCurrentLongitude() : ZERO;

        boolean hasSearch = searchLat.compareTo(ZERO) != 0 && searchLon.compareTo(ZERO) != 0;
        boolean hasCurrent = currentLat.compareTo(ZERO) != 0 && currentLon.compareTo(ZERO) != 0;

        if (!hasCurrent) return true;
        if (!hasSearch) return false;

        BigDecimal distanceKm = valueOf(
                haversineDistance(currentLat.doubleValue(), currentLon.doubleValue(), searchLat.doubleValue(), searchLon.doubleValue()));

        return distanceKm.compareTo(valueOf(100.0)) > 0;
    }
}
