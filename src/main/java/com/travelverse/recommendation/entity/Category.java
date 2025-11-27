package com.travelverse.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = -2032066990096839905L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
