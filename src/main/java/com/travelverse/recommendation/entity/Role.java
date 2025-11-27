package com.travelverse.recommendation.entity;

import com.travelverse.recommendation.enums.RoleId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 5543020172547462207L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @Enumerated(STRING)
    @ToString.Include
    private RoleId name;

    @CreationTimestamp
    private LocalDateTime created;
}
