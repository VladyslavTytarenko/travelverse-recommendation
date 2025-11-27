package com.travelverse.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class WeeklyTrafficStatsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5494600385780670923L;

    private BigDecimal weeklyMean;

    private BigDecimal weeklyPeak;

    private int quietHours;

    private int peakHours;
}
