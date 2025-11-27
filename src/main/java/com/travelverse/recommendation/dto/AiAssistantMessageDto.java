package com.travelverse.recommendation.dto;

import com.travelverse.recommendation.enums.MessageSender;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
public class AiAssistantMessageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6085042982179999307L;

    private Long id;

    private Long dialogId;

    @NotNull(message = "Message sender is missing!")
    private MessageSender messageSender;

    @NotNull(message = "Message is missing!")
    private Map<String, Object> message;

    private OffsetDateTime created;
}
