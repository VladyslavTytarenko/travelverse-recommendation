package com.travelverse.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class CategoryLocale implements Serializable {

    @Serial
    private static final long serialVersionUID = 2416214976898345048L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private Long categoryId;

    @NotBlank
    private String value;

    @NotBlank
    private String locale;
}
