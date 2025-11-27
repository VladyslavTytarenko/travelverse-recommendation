package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.Web3Entity;
import com.travelverse.recommendation.entity.projection.Web3EntityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface Web3EntityRepository extends JpaRepository<Web3Entity, Long> {

    @Query(value = """
            SELECT 
                e.id AS id,
                e.code AS code,
                el.name AS name,
                el.description AS description,
                el.address AS address,
                e.latitude AS latitude,
                e.longitude AS longitude,
                e.object_type_id AS objectTypeId,
                elt.value AS objectType,
                ee.reviews AS reviews,
                e.expert_rating AS expertRating,
                ee.rating AS rating,
                ee.price_level AS priceLevel,
                ee.venue_id AS venueId,
                e.is_hidden_gem AS isHiddenGem,
                e.business_score AS businessScore,
                (
                    6371 * acos(
                        cos(radians(:lat)) * cos(radians(e.latitude)) *
                        cos(radians(e.longitude) - radians(:lng)) +
                        sin(radians(:lat)) * sin(radians(e.latitude))
                    )
                ) AS distance
            FROM entity e
            LEFT JOIN entity_external_data ee ON e.id = ee.entity_id
            LEFT JOIN (
                SELECT 
                    el_inner.*,
                    ROW_NUMBER() OVER (
                        PARTITION BY el_inner.entity_id
                        ORDER BY 
                            CASE 
                                WHEN el_inner.locale_code = :locale THEN 1
                                WHEN el_inner.locale_code = 'en' THEN 2
                                ELSE 3
                            END
                    ) AS rn
                FROM entity_locale el_inner
            ) el ON el.entity_id = e.id AND el.rn = 1
            LEFT JOIN entity_type et ON et.id = e.object_type_id
            LEFT JOIN entity_type_locale elt ON elt.entity_type_id = et.id AND elt.locale_code = :locale
            WHERE e.status = 'active'
              AND e.entity_status = 'APPROVED'
              AND (
                     :excludedEntityTypeIds IS NULL
                     OR e.object_type_id NOT IN (:excludedEntityTypeIds)
                   )
            HAVING distance < :radius
            ORDER BY distance
            """, nativeQuery = true)
    List<Web3EntityProjection> findWithinRadius(@Param("lat") double lat, @Param("lng") double lng, @Param("radius") double radiusKm, @Param("locale") String locale, @Param("excludedEntityTypeIds") Set<Long> excludedEntityTypeIds);
}
