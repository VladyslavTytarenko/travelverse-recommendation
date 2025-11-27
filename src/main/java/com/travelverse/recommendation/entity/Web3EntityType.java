package com.travelverse.recommendation.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Web3EntityType {

    @Serial
    private static final long serialVersionUID = -3417156760693742087L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
}
