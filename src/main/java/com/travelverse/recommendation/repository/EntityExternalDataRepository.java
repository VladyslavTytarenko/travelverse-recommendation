package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.EntityExternalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EntityExternalDataRepository extends JpaRepository<EntityExternalData, Long> {

    @Query("SELECT d FROM EntityExternalData d WHERE d.entityId = :entityId")
    Optional<EntityExternalData> findByEntityId(@Param("entityId") Long entityId);

    @Query("SELECT venueId FROM EntityExternalData")
    List<String> findAllVenues();

    @Query("SELECT updated FROM EntityExternalData WHERE entityId = :entityId")
    OffsetDateTime findUpdatedByEntityId(@Param("entityId") Long entityId);

    List<EntityExternalData> findAllByVenueIdIn(@Param("venueIds") Set<String> venueIds);
}
