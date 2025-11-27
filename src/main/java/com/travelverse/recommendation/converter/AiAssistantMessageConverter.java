package com.travelverse.recommendation.converter;

import com.travelverse.recommendation.dto.AiAssistantMessageDto;
import com.travelverse.recommendation.dto.PageResultDto;
import com.travelverse.recommendation.entity.AiAssistantMessage;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = SPRING, nullValueCheckStrategy = ALWAYS)
public abstract class AiAssistantMessageConverter {

    public abstract AiAssistantMessageDto toDto(AiAssistantMessage message);

    public abstract AiAssistantMessage toEntity(AiAssistantMessageDto message);

    public PageResultDto<AiAssistantMessageDto> toDtoPage(Page<AiAssistantMessage> page) {
        List<AiAssistantMessageDto> messages = page.map(this::toDto).getContent();

        return new PageResultDto<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                messages);
    }
}
