package com.bobocode.hw19.model;

public record Person(
        String firstName,
        String lastName,
        int reactiveProgrammingLevel,
        boolean hasSpringWebFluxExperience
) {
}
