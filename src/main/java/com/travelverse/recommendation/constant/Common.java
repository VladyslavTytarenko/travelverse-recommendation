package com.travelverse.recommendation.constant;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

public interface Common {

    String ID = "id";

    int ONE_HUNDRED = 100;
    String EN = "en";
    int ONE_STAR_VALUE = 20;

    // Transportation types
    String WALK = "walk";
    String BIKE = "bike";
    String PUBLIC_TRANSPORT = "public_transport";
    String CAR = "car";

    // Search radius and geo
    int DEFAULT_RADIUS_IN_KM = 30;
    int DEFAULT_RADIUS_IN_METERS = 100_000;
    double EARTH_RADIUS_KM = 6371.0;

    // Crowd preferences
    int AVOID_CROWD = 1;
    int LIKE_CROWD = 2;
    int NEUTRAL_CROWD = 3;
    BigDecimal DEFAULT_TRAFFIC = BigDecimal.valueOf(0.1);
    BigDecimal HOURS_IN_WEEK = BigDecimal.valueOf(7 * 24);

    // Hidden gem preferences
    int CALM_AND_QUIET = 1;
    int DEPENDS_ON_VIBE = 2;
    int LIVELY = 3;

    BigDecimal EPSILON = valueOf(1e-3);

    // BestTime API parameters
    String VENUE_ADDRESS = "venue_address";
    String VENUE_NAME = "venue_name";
    String API_KEY = "api_key_private";
    String COLLECTION_ID = "collection_id";
    String RADIUS = "radius";
    String LIVE = "live";
    String LATITUDE = "lat";
    String LONGITUDE = "lng";
    String TOTAL_SCORE = "total_score";

    // General
    BigDecimal HUNDRED = valueOf(100);

    // Weights
    String CATEGORY = "category";
    String DISTANCE = "distance";
    String QUALITY = "quality";
    String BUDGET = "budget";
    String CROWD = "crowd";
    String HIDDEN_GEM = "hiddenGem";
    String BUSINESS = "business";

    // Week
    String MONDAY = "monday";
    String TUESDAY = "tuesday";
    String WEDNESDAY = "wednesday";
    String THURSDAY = "thursday";
    String FRIDAY = "friday";
    String SATURDAY = "saturday";
    String SUNDAY = "sunday";

    // External data
    String DAY_INFO = "day_info";
    String BUSY_HOURS = "busy_hours";
    String QUIET_HOURS = "quiet_hours";
    String PEAK_HOURS = "peak_hours";
    String SURGE_HOURS = "surge_hours";
    String HOUR_ANALYSIS = "hour_analysis";
    String DAY_RAW = "day_raw";
    String DAY_MEAN = "day_mean";
    String DAY_MAX = "day_max";

    // Rating
    BigDecimal MAX_RATING = valueOf(5);
}
