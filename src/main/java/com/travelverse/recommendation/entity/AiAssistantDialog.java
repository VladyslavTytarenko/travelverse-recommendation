package com.travelverse.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Accessors(chain = true)
public class AiAssistantDialog implements Serializable {

    @Serial
    private static final long serialVersionUID = 7266915539984136112L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private Long accountId;

    @CreationTimestamp
    private OffsetDateTime created;
}
