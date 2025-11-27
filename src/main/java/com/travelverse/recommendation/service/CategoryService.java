package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.CategoryDto;
import com.travelverse.recommendation.dto.EntityTypeDto;
import com.travelverse.recommendation.dto.SubcategoryDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Set;

@Validated
public interface CategoryService {

    List<CategoryDto> findAllCategories(@NotBlank String locale);

    List<SubcategoryDto> findAllSubcategoriesByCategoryId(@NotNull Long categoryId, @NotBlank String locale);

    List<EntityTypeDto> findEntityTypesBySubcategoryId(@NotNull Set<Long> subcategoryIds);

    Set<Long> findAllEntityTypeIdsFromSubcategoryByEntityTypeIds(Set<Long> entityTypeIds);
}
