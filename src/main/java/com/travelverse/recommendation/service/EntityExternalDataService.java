package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.EntityExternalDataDto;
import com.travelverse.recommendation.dto.VenueSearchResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EntityExternalDataService {

    Map<String, VenueSearchResponseDto.VenueDto> findVenueLiveData(double currentLatitude, double currentLongitude);

    List<EntityExternalDataDto> findForcastByVenueIds(Set<String> venueIds);
}
