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
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Accessors(chain = true)
public class EntityRecommendationHistory implements Serializable {

    @Serial
    private static final long serialVersionUID = 8401686487109800611L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private Long entityAccountPreferencesId;

    @NotNull
    private Long entityId;

    private Integer traffic;

    @NotNull
    private BigDecimal score;

    private BigDecimal categoryScore;

    private BigDecimal distanceScore;

    private BigDecimal qualityScore;

    private BigDecimal budgetScore;

    private BigDecimal crowdScore;

    private BigDecimal hiddenGemScore;

    private BigDecimal businessScore;

    @CreationTimestamp
    private OffsetDateTime created;
}
