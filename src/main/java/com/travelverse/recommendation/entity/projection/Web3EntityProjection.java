package com.travelverse.recommendation.entity.projection;

import java.math.BigDecimal;

public interface Web3EntityProjection {

    Long getId();

    String getCode();

    String getName();

    String getDescription();

    String getAddress();

    Double getLatitude();

    Double getLongitude();

    Long getObjectTypeId();

    String getObjectType();

    Long getReviews();

    BigDecimal getExpertRating();

    BigDecimal getRating();

    Integer getPriceLevel();

    String getVenueId();

    Boolean getIsHiddenGem();

    Integer getBusinessScore();
}
