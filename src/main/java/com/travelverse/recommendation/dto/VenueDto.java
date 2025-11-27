package com.travelverse.recommendation.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class VenueDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5373209723601645539L;

    private Long id;

    private Long entityId;

    private String status;

    private VenueInfo venueInfo;

    private List<DayAnalysis> analysis;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class VenueInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = -4785727176557493058L;

        private String venueId;

        private String venueName;

        private String venueAddress;

        private List<String> venueAddressList;

        private String venueTimezone;

        private int venueDwellTimeMin;

        private int venueDwellTimeMax;

        private int venueDwellTimeAvg;

        private String venueType;

        private List<String> venueTypes;

        private double venueLat;

        private double venueLon;

        private BigDecimal rating;

        private long reviews;

        private int priceLevel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class DayAnalysis implements Serializable {

        @Serial
        private static final long serialVersionUID = 209707853392715600L;

        private DayInfo dayInfo;

        private List<Integer> busyHours;

        private List<Integer> quietHours;

        private List<PeakHour> peakHours;

        private SurgeHours surgeHours;

        private List<HourAnalysis> hourAnalysis;

        private List<Integer> dayRaw;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class DayInfo implements Serializable {

        @Serial
        private static final long serialVersionUID = 5602326673559382233L;

        private int dayInt;

        private String dayText;

        private String venueOpen;

        private String venueClosed;

        private int dayRankMean;

        private int dayRankMax;

        private int dayMean;

        private int dayMax;

        private VenueOpenClose venueOpenCloseV2;

        private String note;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class VenueOpenClose implements Serializable {

        @Serial
        private static final long serialVersionUID = 3646999173038960554L;

        private List<OpenClose24> _24h;

        private List<String> _12h;

        private String specialDay;

        private boolean open24h;

        private boolean crossesMidnight;

        private String dayText;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonNaming(SnakeCaseStrategy.class)
        public static class OpenClose24 implements Serializable {

            @Serial
            private static final long serialVersionUID = -4541604051214301280L;

            private int opens;

            private int closes;

            private int opensMinutes;

            private int closesMinutes;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class PeakHour implements Serializable {

        @Serial
        private static final long serialVersionUID = -6013676902653749296L;

        private int peakStart;

        private int peakMax;

        private int peakEnd;

        private int peakIntensity;

        private int peakDeltaMeanWeek;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class SurgeHours implements Serializable {

        @Serial
        private static final long serialVersionUID = 4037091303480824423L;

        private String mostPeopleCome;

        private String mostPeopleLeave;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class HourAnalysis implements Serializable {

        @Serial
        private static final long serialVersionUID = -3087200856160986706L;

        private int hour;

        private String intensityTxt;

        private int intensityNr;
    }
}
