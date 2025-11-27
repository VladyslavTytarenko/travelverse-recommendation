package com.travelverse.recommendation.service.impl;

import com.travelverse.recommendation.converter.AiAssistantMessageConverter;
import com.travelverse.recommendation.dto.AiAssistantMessageDto;
import com.travelverse.recommendation.dto.PageResultDto;
import com.travelverse.recommendation.entity.AiAssistantDialog;
import com.travelverse.recommendation.entity.AiAssistantMessage;
import com.travelverse.recommendation.repository.AiAssistantDialogRepository;
import com.travelverse.recommendation.repository.AiAssistantMessageRepository;
import com.travelverse.recommendation.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.travelverse.recommendation.constant.Common.ID;
import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AiAssistantServiceImpl implements AiAssistantService {

    private final AiAssistantDialogRepository aiAssistantDialogRepository;
    private final AiAssistantMessageRepository aiAssistantMessageRepository;
    private final AiAssistantMessageConverter aiAssistantMessageConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiAssistantMessageDto createOrUpdate(AiAssistantMessageDto message, Long accountId) {
        processDialog(message, accountId);

        return aiAssistantMessageConverter.toDto(
                aiAssistantMessageRepository.save(
                        aiAssistantMessageConverter.toEntity(message)));
    }

    @Override
    public PageResultDto<AiAssistantMessageDto> findByDialogId(Long dialogId, Integer page, Integer size, Long accountId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(ID).descending());
        Page<AiAssistantMessage> aiAssistantMessages = aiAssistantMessageRepository.findByDialogId(dialogId, pageable);

        return aiAssistantMessageConverter.toDtoPage(aiAssistantMessages);
    }

    @Override
    public Long findCurrentDialogId(Long accountId) {
        Long currentDialogId = aiAssistantDialogRepository.findCurrentDialogId(accountId);
        if (isNull(currentDialogId)) throw new RuntimeException("Can't find current dialog ID");

        return currentDialogId;
    }

    private void processDialog(AiAssistantMessageDto message, Long accountId) {
        Long dialogId = message.getDialogId();
        if (isNull(dialogId)) {
            AiAssistantDialog dialog = new AiAssistantDialog().setAccountId(accountId);
            dialogId = aiAssistantDialogRepository.save(dialog).getId();
            message.setDialogId(dialogId);
        } else {
            boolean isDialogExists = aiAssistantDialogRepository.existsByIdAndAccountId(dialogId, accountId);
            if (FALSE.equals(isDialogExists)) throw new RuntimeException("Can't find dialog by ID");
        }
    }
}
