package com.travelverse.recommendation.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.JSON;

@Data
@Entity
@Accessors(chain = true)
public class EntityExternalData implements Serializable {

    @Serial
    private static final long serialVersionUID = -8611602649277451953L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long entityId;

    private String venueId;

    private String venueTimezone;

    private Integer venueDwellTimeMin;

    private Integer venueDwellTimeMax;

    private Integer venueDwellTimeAvg;

    private BigDecimal rating;

    private Integer reviews;

    private Integer priceLevel;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> monday;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> tuesday;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> wednesday;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> thursday;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> friday;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> saturday;

    @JdbcTypeCode(JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> sunday;

    @CreationTimestamp
    private OffsetDateTime created;

    @UpdateTimestamp
    private OffsetDateTime updated;
}
