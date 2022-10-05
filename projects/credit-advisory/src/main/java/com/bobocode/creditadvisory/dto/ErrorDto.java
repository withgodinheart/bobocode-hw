package com.bobocode.creditadvisory.dto;

import javax.validation.constraints.NotBlank;

public record ErrorDto(@NotBlank(message = "cannot be null or blank") String result,
                       @NotBlank(message = "cannot be null or blank") String message) {
}
