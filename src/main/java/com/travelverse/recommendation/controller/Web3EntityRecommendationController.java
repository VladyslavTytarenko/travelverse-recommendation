package com.travelverse.recommendation.controller;

import com.travelverse.recommendation.dto.Web3EntityRecommendationDto;
import com.travelverse.recommendation.model.UserPrincipal;
import com.travelverse.recommendation.service.Web3EntityRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.travelverse.recommendation.constant.Routes.ENTITY_ROUTE;
import static com.travelverse.recommendation.constant.Routes.RECOMMENDATION_ROUTE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class Web3EntityRecommendationController {

    private final Web3EntityRecommendationService web3EntityRecommendationService;

    @PostMapping(ENTITY_ROUTE + RECOMMENDATION_ROUTE)
    public List<Web3EntityRecommendationDto> recommendAndCreate(UserPrincipal userPrincipal) {
        return web3EntityRecommendationService.recommendAndCreate(userPrincipal.getId());
    }
}
