package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.Web3EntityType;
import com.travelverse.recommendation.entity.projection.EntityTypeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface Web3EntityTypeRepository extends JpaRepository<Web3EntityType, Long> {

    @Query(value = """
        SELECT et.id as id
        FROM entity_type et
        LEFT JOIN entity_type_subcategory ets ON et.id = ets.entity_type_id
        WHERE ets.subcategory_id IN (:subcategoryId)
    """, nativeQuery = true)
    List<EntityTypeProjection> findAllBySubcategoryIdIn(@Param("subcategoryId") Set<Long> subcategoryIds);

    @Query(value = """
        SELECT DISTINCT et.id
        FROM entity_type et
        JOIN entity_type_subcategory ets ON et.id = ets.entity_type_id
        WHERE ets.subcategory_id IN (
            SELECT subcategory_id
            FROM entity_type_subcategory
            WHERE entity_type_id IN (:entityTypeIds)
        )
    """, nativeQuery = true)
    Set<Long> findAllEntityTypeIdsFromSubcategoryByEntityTypeIds(@Param("entityTypeIds") Set<Long> entityTypeIds);
}
