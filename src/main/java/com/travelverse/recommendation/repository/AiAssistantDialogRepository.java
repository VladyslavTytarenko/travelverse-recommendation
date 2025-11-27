package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.AiAssistantDialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AiAssistantDialogRepository extends JpaRepository<AiAssistantDialog, Long> {

    boolean existsByIdAndAccountId(@Param("dialogId") Long dialogId, @Param("accountId") Long accountId);

    @Query("""
        SELECT d.id FROM AiAssistantDialog d
        WHERE d.accountId = :accountId
        ORDER BY d.created DESC
        LIMIT 1
    """)
    Long findCurrentDialogId(@Param("accountId") Long accountId);
}
