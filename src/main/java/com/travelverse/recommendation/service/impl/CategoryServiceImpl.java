package com.travelverse.recommendation.service.impl;

import com.travelverse.recommendation.converter.CategoryConverter;
import com.travelverse.recommendation.dto.CategoryDto;
import com.travelverse.recommendation.dto.EntityTypeDto;
import com.travelverse.recommendation.dto.SubcategoryDto;
import com.travelverse.recommendation.repository.CategoryRepository;
import com.travelverse.recommendation.repository.SubcategoryRepository;
import com.travelverse.recommendation.repository.Web3EntityTypeRepository;
import com.travelverse.recommendation.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final Web3EntityTypeRepository web3EntityTypeRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryDto> findAllCategories(String locale) {
        return categoryConverter.toCategoryDtos(
                categoryRepository.findAllWithLocale(locale));
    }

    @Override
    public List<SubcategoryDto> findAllSubcategoriesByCategoryId(Long categoryId, String locale) {
        return categoryConverter.toSubcategoryDtos(
                subcategoryRepository.findAllByCategoryId(categoryId, locale));
    }

    @Override
    public List<EntityTypeDto> findEntityTypesBySubcategoryId(Set<Long> subcategoryIds) {
        return categoryConverter.toEntityTypeDtos(
                web3EntityTypeRepository.findAllBySubcategoryIdIn(subcategoryIds));
    }

    @Override
    public Set<Long> findAllEntityTypeIdsFromSubcategoryByEntityTypeIds(Set<Long> entityTypeIds) {
        return web3EntityTypeRepository.findAllEntityTypeIdsFromSubcategoryByEntityTypeIds(entityTypeIds);
    }
}
