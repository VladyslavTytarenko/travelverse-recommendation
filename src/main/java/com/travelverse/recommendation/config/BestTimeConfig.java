package com.travelverse.recommendation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class BestTimeConfig {

    @Value("${bestTime.apiUrl}")
    private String apiRootUrl;

    @Getter
    @Value("${bestTime.apiKey}")
    private String apiKey;

    @Getter
    @Value("${bestTime.api.findVenue}")
    private String findVenuePath;

    @Getter
    @Value("${bestTime.api.venueFilter}")
    private String venueFilterPath;

    @Getter
    @Value("${bestTime.api.addVenueToCollection}")
    private String addVenueToCollectionPath;

    @Getter
    @Value("${bestTime.collectionId}")
    private String collectionId;

    public WebClient bestTimeWebClient() {
        return WebClient.builder()
                .baseUrl(apiRootUrl)
                .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }
}
