package com.travelverse.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class Web3EntityRecommendationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1655344016260261969L;

    private Long id;

    private String code;

    private String image;

    private String name;

    private String description;

    private String objectType;

    private Double longitude;

    private Double latitude;

    private String address;

    private BigDecimal score;

    private Score scoreDetails;

    private Long eventId;

    private String eventPath;

    @Data
    @Accessors(chain = true)
    public static class Score implements Serializable {

        @Serial
        private static final long serialVersionUID = -3245321088768467650L;

        private BigDecimal categoryScore;

        private BigDecimal distanceScore;

        private BigDecimal qualityScore;

        private BigDecimal budgetScore;

        private BigDecimal crowdScore;

        private BigDecimal hiddenGemScore;

        private BigDecimal businessScore;
    }
}
