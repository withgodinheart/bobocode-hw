package com.bobocode.creditadvisory.dto;

import javax.validation.constraints.NotBlank;

public record SuccessDto(@NotBlank(message = "cannot be null or blank") Long applicationId) {
}
