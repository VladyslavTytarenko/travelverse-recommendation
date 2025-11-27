package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.EntityAccountPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EntityAccountPreferencesRepository extends JpaRepository<EntityAccountPreferences, Long> {

    Optional<EntityAccountPreferences> findFirstByAccountIdOrderByCreatedDesc(@Param("accountId") Long accountId);

    Optional<EntityAccountPreferences> findByIdAndAccountId(@Param("id") Long id, @Param("accountId") Long accountId);
}
