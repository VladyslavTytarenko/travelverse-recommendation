package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.Subcategory;
import com.travelverse.recommendation.entity.projection.SubcategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    @Query("""
            SELECT s.id as id, s.categoryId as categoryId, sl.value as value, sl.locale as locale FROM Subcategory s
            LEFT JOIN SubcategoryLocale sl ON s.id = sl.subcategoryId
            WHERE sl.locale = :locale AND s.categoryId = :categoryId
    """)
    List<SubcategoryProjection> findAllByCategoryId(@Param("categoryId") Long categoryId, @Param("locale") String locale);
}
