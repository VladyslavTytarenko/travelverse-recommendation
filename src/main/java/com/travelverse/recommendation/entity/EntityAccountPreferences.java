package com.travelverse.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Map;

import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.JSON;

@Data
@Entity
@Accessors(chain = true)
public class EntityAccountPreferences implements Serializable {

    @Serial
    private static final long serialVersionUID = 7447967258101270105L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @JdbcTypeCode(JSON)
    private Map<String, Object> preferences;

    private Long accountId;

    @CreationTimestamp
    private OffsetDateTime created;

    @UpdateTimestamp
    private OffsetDateTime updated;
}
