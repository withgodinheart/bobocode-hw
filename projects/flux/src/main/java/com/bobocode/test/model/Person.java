package com.bobocode.test.model;

public record Person(
        String firstName,
        String lastName,
        int reactiveProgrammingLevel,
        boolean hasSpringWebFluxExperience
) {
}
