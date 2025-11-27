package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.Category;
import com.travelverse.recommendation.entity.projection.CategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            SELECT c.id as id, cl.value as value, cl.locale as locale FROM Category c
            LEFT JOIN CategoryLocale cl ON c.id = cl.categoryId
            WHERE cl.locale = :locale
    """)
    List<CategoryProjection> findAllWithLocale(String locale);
}
