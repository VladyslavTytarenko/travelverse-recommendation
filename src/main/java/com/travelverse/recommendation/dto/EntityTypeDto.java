package com.travelverse.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class EntityTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 3704089341115205522L;

    private Long id;
}
