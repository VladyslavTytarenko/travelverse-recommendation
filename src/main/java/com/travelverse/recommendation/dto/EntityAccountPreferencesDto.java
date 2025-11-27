package com.travelverse.recommendation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@Data
public class EntityAccountPreferencesDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6896043223926550986L;

    private Long id;

    @NotNull(message = "Account id is missing!")
    private Long accountId;

    @NotNull(message = "Preferences is missing!")
    private Map<String, Object> preferences;
}
