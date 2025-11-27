package com.travelverse.recommendation.entity;

import com.travelverse.recommendation.enums.EntityStatus;
import lombok.Data;
import org.hibernate.annotations.*;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.travelverse.recommendation.enums.EntityStatus.DRAFT;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Web3Entity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3801281406118760165L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank(message = "Code is missing!")
    private String code;

    @Enumerated(STRING)
    @NotNull(message = "Status is missing!")
    private EntityStatus entityStatus = DRAFT;

    @NotNull(message = "Status changed by is missing!")
    private Long statusChangedBy;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    private Boolean isRecommended = false;

    private Long pointsReward;

    private Boolean isDeleted = false;

    private Boolean isGeoRequired = true;

    private Double latitude;

    private Double longitude;

    private BigDecimal expertRating;

    private Integer businessScore;

    private Boolean isHiddenGem;

    private Boolean isExternalData;

    private Long createdBy;

    @CreationTimestamp
    private OffsetDateTime created;

    private Long updatedBy;

    @UpdateTimestamp
    private OffsetDateTime updated;
}
