package com.travelverse.recommendation.controller;

import com.travelverse.recommendation.dto.EntityAccountPreferencesDto;
import com.travelverse.recommendation.model.UserPrincipal;
import com.travelverse.recommendation.service.EntityAccountPreferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.travelverse.recommendation.constant.Routes.ENTITY_ACCOUNT_PREFERENCES_ROUTE;
import static com.travelverse.recommendation.constant.Routes.LAST_ROUTE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EntityAccountPreferencesController {

    private final EntityAccountPreferencesService entityAccountPreferencesService;

    @PostMapping(ENTITY_ACCOUNT_PREFERENCES_ROUTE)
    public EntityAccountPreferencesDto createOrUpdateEntityAccountPreferences(UserPrincipal userPrincipal,
                                                                              @RequestBody EntityAccountPreferencesDto preferences) {

        preferences.setAccountId(userPrincipal.getId());
        return entityAccountPreferencesService.createOrUpdate(preferences);
    }

    @GetMapping(ENTITY_ACCOUNT_PREFERENCES_ROUTE + LAST_ROUTE)
    public EntityAccountPreferencesDto findLastByAccountId(UserPrincipal userPrincipal) {
        return entityAccountPreferencesService.findLastByAccountId(userPrincipal.getId());
    }

    @GetMapping(ENTITY_ACCOUNT_PREFERENCES_ROUTE + "/{id}")
    public EntityAccountPreferencesDto findById(UserPrincipal userPrincipal,
                                                @PathVariable("id") Long id) {
        return entityAccountPreferencesService.findById(id, userPrincipal.getId());
    }
}
