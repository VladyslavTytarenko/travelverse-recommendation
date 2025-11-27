package com.travelverse.recommendation.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import static com.travelverse.recommendation.constant.Common.EN;
import static com.travelverse.recommendation.constant.Common.WALK;
import static org.hibernate.type.descriptor.java.IntegerJavaType.ZERO;

@Data
public class Web3EntityRecommendationCriteria implements Serializable {

    @Serial
    private static final long serialVersionUID = -3961893611193482189L;

    private BigDecimal searchLongitude;

    private BigDecimal searchLatitude;

    private BigDecimal currentLongitude;

    private BigDecimal currentLatitude;

    private String travelMode = WALK;

    private Integer budgetPreference;

    private Integer crowdPreference;

    private Integer hiddenGemPreference;

    private Set<Long> entityTypeIds;

    private Set<Long> excludedEntityTypeIds;

    private String locale = EN;

    private int attempt = ZERO;
}
