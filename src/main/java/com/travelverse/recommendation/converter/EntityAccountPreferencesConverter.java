package com.travelverse.recommendation.converter;

import com.travelverse.recommendation.dto.EntityAccountPreferencesDto;
import com.travelverse.recommendation.entity.EntityAccountPreferences;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = SPRING, nullValueCheckStrategy = ALWAYS)
public abstract class EntityAccountPreferencesConverter {

    public abstract EntityAccountPreferencesDto toDto(EntityAccountPreferences history);

    public abstract EntityAccountPreferences toEntity(EntityAccountPreferencesDto history);
}
