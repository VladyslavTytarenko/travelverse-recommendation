package com.travelverse.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record PageResultDto<T>(Integer page,
							   Integer pageSize,
							   Long totalElements,
							   Integer totalPages,
							   List<T> content) {}
