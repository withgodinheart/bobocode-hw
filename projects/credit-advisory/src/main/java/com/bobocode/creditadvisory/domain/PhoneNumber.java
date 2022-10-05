package com.bobocode.creditadvisory.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class PhoneNumber {

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private PhoneType type;

    enum PhoneType {
        WORK, HOME, MOBILE
    }
}
