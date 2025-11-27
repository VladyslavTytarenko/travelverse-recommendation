package com.travelverse.recommendation.controller;

import com.travelverse.recommendation.dto.AiAssistantMessageDto;
import com.travelverse.recommendation.dto.PageResultDto;
import com.travelverse.recommendation.model.UserPrincipal;
import com.travelverse.recommendation.service.AiAssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.travelverse.recommendation.constant.Routes.AI_ASSISTANT_ROUTE;
import static com.travelverse.recommendation.constant.Routes.CURRENT_ROUTE;
import static com.travelverse.recommendation.constant.Routes.DIALOG_ROUTE;
import static com.travelverse.recommendation.constant.Routes.MESSAGE_ROUTE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AiAssistantController {

    private final AiAssistantService aiAssistantService;

    @PostMapping(AI_ASSISTANT_ROUTE + MESSAGE_ROUTE)
    AiAssistantMessageDto createOrUpdateAiAssistantMessage(UserPrincipal userPrincipal,
                                                           @RequestBody AiAssistantMessageDto message) {
        return aiAssistantService.createOrUpdate(message, userPrincipal.getId());
    }

    @GetMapping(AI_ASSISTANT_ROUTE + DIALOG_ROUTE + "/{dialogId}" + MESSAGE_ROUTE)
    PageResultDto<AiAssistantMessageDto> findAiAssistantMessages(UserPrincipal userPrincipal,
                                                                 @PathVariable("dialogId") Long dialogId,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        return aiAssistantService.findByDialogId(dialogId, page, size, userPrincipal.getId());
    }

    @GetMapping(AI_ASSISTANT_ROUTE + DIALOG_ROUTE + CURRENT_ROUTE)
    Long findCurrentDialogId(UserPrincipal userPrincipal) {
        return aiAssistantService.findCurrentDialogId(userPrincipal.getId());
    }
}
