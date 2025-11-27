package com.travelverse.recommendation.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {

    REGULAR("regular"),
    BOT("bot"),
    STAFF("staff");

    private final String value;
}
