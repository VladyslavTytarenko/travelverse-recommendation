package com.travelverse.recommendation.service.impl;

import com.travelverse.recommendation.config.BestTimeConfig;
import com.travelverse.recommendation.converter.EntityExternalDataConverter;
import com.travelverse.recommendation.dto.EntityExternalDataDto;
import com.travelverse.recommendation.dto.VenueSearchResponseDto;
import com.travelverse.recommendation.repository.EntityExternalDataRepository;
import com.travelverse.recommendation.service.EntityExternalDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.travelverse.recommendation.constant.Common.API_KEY;
import static com.travelverse.recommendation.constant.Common.COLLECTION_ID;
import static com.travelverse.recommendation.constant.Common.DEFAULT_RADIUS_IN_METERS;
import static com.travelverse.recommendation.constant.Common.LATITUDE;
import static com.travelverse.recommendation.constant.Common.LIVE;
import static com.travelverse.recommendation.constant.Common.LONGITUDE;
import static com.travelverse.recommendation.constant.Common.RADIUS;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntityExternalDataServiceImpl implements EntityExternalDataService {

    private final EntityExternalDataRepository entityExternalDataRepository;
    private final EntityExternalDataConverter entityExternalDataConverter;
    private final BestTimeConfig bestTimeConfig;

    @Override
    public Map<String, VenueSearchResponseDto.VenueDto> findVenueLiveData(double currentLatitude, double currentLongitude) {
        try {
            VenueSearchResponseDto response = fetchLiveVenueData(currentLatitude, currentLongitude);

            return ofNullable(response)
                    .map(VenueSearchResponseDto::getVenues)
                    .map(venues -> venues.stream()
                            .collect(Collectors.toMap(VenueSearchResponseDto.VenueDto::getVenueId, Function.identity())))
                    .orElseGet(Collections::emptyMap);

        } catch (Exception e) {
            log.error("Unable to find venue data", e);
            return Collections.emptyMap();
        }
    }

    @Override
    public List<EntityExternalDataDto> findForcastByVenueIds(Set<String> venueIds) {
        return entityExternalDataConverter.toDtos(
                entityExternalDataRepository.findAllByVenueIdIn(venueIds));
    }

    private VenueSearchResponseDto fetchLiveVenueData(double latitude, double longitude) {
        return bestTimeConfig.bestTimeWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(bestTimeConfig.getVenueFilterPath())
                        .queryParam(API_KEY, bestTimeConfig.getApiKey())
                        .queryParam(COLLECTION_ID, bestTimeConfig.getCollectionId())
                        .queryParam(RADIUS, DEFAULT_RADIUS_IN_METERS)
                        .queryParam(LATITUDE, latitude)
                        .queryParam(LONGITUDE, longitude)
                        .queryParam(LIVE, true)
                        .build())
                .retrieve()
                .bodyToMono(VenueSearchResponseDto.class)
                .block();
    }
}
