package com.travelverse.recommendation.service;

import com.travelverse.recommendation.dto.AiAssistantMessageDto;
import com.travelverse.recommendation.dto.PageResultDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AiAssistantService {

    AiAssistantMessageDto createOrUpdate(@NotNull AiAssistantMessageDto message, @NotNull Long accountId);

    PageResultDto<AiAssistantMessageDto> findByDialogId(@NotNull Long dialogId, @NotNull Integer page, @NotNull Integer size, @NotNull Long accountId);

    Long findCurrentDialogId(@NotNull Long accountId);
}
