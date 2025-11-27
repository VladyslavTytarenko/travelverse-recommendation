package com.travelverse.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
public class EntityExternalDataDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -8351642196392940091L;

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

    private Map<String, Object> monday;

    private Map<String, Object> tuesday;

    private Map<String, Object> wednesday;

    private Map<String, Object> thursday;

    private Map<String, Object> friday;

    private Map<String, Object> saturday;

    private Map<String, Object> sunday;

    private OffsetDateTime created;

    private OffsetDateTime updated;
}
