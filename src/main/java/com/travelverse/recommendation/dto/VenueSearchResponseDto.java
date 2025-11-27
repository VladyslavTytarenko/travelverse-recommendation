package com.travelverse.recommendation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class VenueSearchResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -140186645820238244L;

    private String status;

    private List<VenueDto> venues;

    private int venuesN;

    private WindowDto window;

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class VenueDto implements Serializable {

        @Serial
        private static final long serialVersionUID = -1446356422554458215L;

        private List<Integer> dayRaw;

        private DayInfoDto dayInfo;

        private Double rating;

        private Integer reviews;

        private Integer priceLevel;

        private String venueId;

        private String venueName;

        private String venueAddress;

        private Double venueLat;

        private Double venueLng;

        private String venueType;

        private Integer venueDwellTimeMin;

        private Integer venueDwellTimeMax;
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class DayInfoDto implements Serializable {

        @Serial
        private static final long serialVersionUID = -7202024135984240665L;

        private Integer dayInt;

        private String dayText;

        private Integer venueOpen;

        private Integer venueClosed;

        private Integer dayRankMean;

        private Integer dayRankMax;

        private Integer dayMean;

        private Integer dayMax;

        private VenueOpenCloseV2 venueOpenCloseV2;

        private String note;
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class VenueOpenCloseV2 implements Serializable {

        @Serial
        private static final long serialVersionUID = 8862956318112425918L;

        private List<Object> _24h;

        private List<Object> _12h;
    }

    @Data
    @JsonNaming(SnakeCaseStrategy.class)
    public static class WindowDto implements Serializable {

        @Serial
        private static final long serialVersionUID = 7427215638278412813L;

        private Integer timeWindowStart;

        private Integer timeWindowStartIx;

        private String timeWindowStart12h;

        private Integer dayWindowStartInt;

        private String dayWindowStartTxt;

        private Integer dayWindowEndInt;

        private String dayWindowEndTxt;

        private Integer timeWindowEnd;

        private Integer timeWindowEndIx;

        private String timeWindowEnd12h;

        private String dayWindow;

        private Integer timeLocal;

        private String timeLocal12;

        private Integer timeLocalIndex;
    }
}