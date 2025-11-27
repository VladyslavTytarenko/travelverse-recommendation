package com.travelverse.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5824243876018277017L;

    private Long id;

    private String value;

    private String locale;
}
