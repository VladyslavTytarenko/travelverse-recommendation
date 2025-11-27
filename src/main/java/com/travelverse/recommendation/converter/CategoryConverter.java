package com.travelverse.recommendation.converter;

import com.travelverse.recommendation.dto.CategoryDto;
import com.travelverse.recommendation.dto.EntityTypeDto;
import com.travelverse.recommendation.dto.SubcategoryDto;
import com.travelverse.recommendation.entity.projection.CategoryProjection;
import com.travelverse.recommendation.entity.projection.EntityTypeProjection;
import com.travelverse.recommendation.entity.projection.SubcategoryProjection;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = SPRING, nullValueCheckStrategy = ALWAYS)
public abstract class CategoryConverter {

    public abstract CategoryDto toDto(CategoryProjection category);

    public List<CategoryDto> toCategoryDtos(List<CategoryProjection> categories) {
        return categories.stream().map(this::toDto).toList();
    }

    public abstract SubcategoryDto toDto(SubcategoryProjection subcategory);

    public List<SubcategoryDto> toSubcategoryDtos(List<SubcategoryProjection> subcategories) {
        return subcategories.stream().map(this::toDto).toList();
    }

    public abstract EntityTypeDto toDto(EntityTypeProjection entityType);

    public List<EntityTypeDto> toEntityTypeDtos(List<EntityTypeProjection> entityTypes) {
        return entityTypes.stream().map(this::toDto).toList();
    }
}
