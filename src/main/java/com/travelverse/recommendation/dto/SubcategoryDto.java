package com.travelverse.recommendation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SubcategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7407326346096720276L;

    private Long id;

    private Long categoryId;

    private String value;

    private String locale;
}
