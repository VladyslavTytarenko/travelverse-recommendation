package com.travelverse.recommendation.repository;

import com.travelverse.recommendation.entity.AiAssistantMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AiAssistantMessageRepository extends JpaRepository<AiAssistantMessage, Long> {

    Page<AiAssistantMessage> findByDialogId(@Param("dialogId") Long dialogId, Pageable pageable);
}
