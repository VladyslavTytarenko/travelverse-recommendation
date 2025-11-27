package com.travelverse.recommendation.converter;

import com.travelverse.recommendation.dto.EntityExternalDataDto;
import com.travelverse.recommendation.dto.VenueDto;
import com.travelverse.recommendation.entity.EntityExternalData;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Map;

import static com.travelverse.recommendation.constant.Common.BUSY_HOURS;
import static com.travelverse.recommendation.constant.Common.DAY_INFO;
import static com.travelverse.recommendation.constant.Common.DAY_RAW;
import static com.travelverse.recommendation.constant.Common.FRIDAY;
import static com.travelverse.recommendation.constant.Common.HOUR_ANALYSIS;
import static com.travelverse.recommendation.constant.Common.MONDAY;
import static com.travelverse.recommendation.constant.Common.PEAK_HOURS;
import static com.travelverse.recommendation.constant.Common.QUIET_HOURS;
import static com.travelverse.recommendation.constant.Common.SATURDAY;
import static com.travelverse.recommendation.constant.Common.SUNDAY;
import static com.travelverse.recommendation.constant.Common.SURGE_HOURS;
import static com.travelverse.recommendation.constant.Common.THURSDAY;
import static com.travelverse.recommendation.constant.Common.TUESDAY;
import static com.travelverse.recommendation.constant.Common.WEDNESDAY;
import static java.util.Objects.isNull;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Slf4j
@Mapper(componentModel = SPRING, nullValueCheckStrategy = ALWAYS)
public abstract class EntityExternalDataConverter {

    public abstract EntityExternalData toEntity(EntityExternalDataDto entityExternalData);

    public abstract EntityExternalDataDto toDto(EntityExternalData entityExternalData);

    @Mapping(source = "venueInfo.venueId", target = "venueId")
    @Mapping(source = "venueInfo.venueTimezone", target = "venueTimezone")
    @Mapping(source = "venueInfo.venueDwellTimeMin", target = "venueDwellTimeMin")
    @Mapping(source = "venueInfo.venueDwellTimeMax", target = "venueDwellTimeMax")
    @Mapping(source = "venueInfo.venueDwellTimeAvg", target = "venueDwellTimeAvg")
    @Mapping(source = "venueInfo.rating", target = "rating")
    @Mapping(source = "venueInfo.reviews", target = "reviews")
    @Mapping(source = "venueInfo.priceLevel", target = "priceLevel")
    @Mapping(target = "monday", ignore = true)
    @Mapping(target = "tuesday", ignore = true)
    @Mapping(target = "wednesday", ignore = true)
    @Mapping(target = "thursday", ignore = true)
    @Mapping(target = "friday", ignore = true)
    @Mapping(target = "saturday", ignore = true)
    @Mapping(target = "sunday", ignore = true)
    public abstract EntityExternalData toEntity(VenueDto venue);

    public List<EntityExternalDataDto> toDtos(List<EntityExternalData> entityExternalData) {
        return entityExternalData.stream().map(this::toDto).toList();
    }

    @AfterMapping
    protected void fillDaysFromVenueDto(@MappingTarget EntityExternalData dest, VenueDto source) {
        if (isNull(source.getAnalysis())) return;

        for (VenueDto.DayAnalysis day : source.getAnalysis()) {
            if (isNull(day.getDayInfo())) continue;
            String dayKey = day.getDayInfo().getDayText().toLowerCase();
            Map<String, Object> map = convertDayAnalysisToMap(day);

            switch (dayKey) {
                case MONDAY -> dest.setMonday(map);
                case TUESDAY -> dest.setTuesday(map);
                case WEDNESDAY -> dest.setWednesday(map);
                case THURSDAY -> dest.setThursday(map);
                case FRIDAY -> dest.setFriday(map);
                case SATURDAY -> dest.setSaturday(map);
                case SUNDAY -> dest.setSunday(map);
                default -> log.warn("Day key {} is not supported!", dayKey);
            }
        }
    }

    private Map<String, Object> convertDayAnalysisToMap(VenueDto.DayAnalysis day) {
        Map<String, Object> map = new java.util.HashMap<>();
        map.put(DAY_INFO, day.getDayInfo());
        map.put(BUSY_HOURS, day.getBusyHours());
        map.put(QUIET_HOURS, day.getQuietHours());
        map.put(PEAK_HOURS, day.getPeakHours());
        map.put(SURGE_HOURS, day.getSurgeHours());
        map.put(HOUR_ANALYSIS, day.getHourAnalysis());
        map.put(DAY_RAW, day.getDayRaw());

        return map;
    }
}
