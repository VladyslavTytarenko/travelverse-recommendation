package com.travelverse.recommendation.entity;

import com.travelverse.recommendation.enums.MessageSender;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.JSON;

@Data
@Entity
@Accessors(chain = true)
public class AiAssistantMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -673923333520860161L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private Long dialogId;

    @Enumerated(STRING)
    private MessageSender messageSender;

    @NotNull
    @JdbcTypeCode(JSON)
    private Map<String, Object> message;

    @CreationTimestamp
    private OffsetDateTime created;
}
