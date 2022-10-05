package com.bobocode.creditadvisory.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;

@MappedSuperclass
@EqualsAndHashCode(of = "cognitoUsername")
@Getter
@Setter
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @NaturalId
    @Column(nullable = false, unique = true, updatable = false)
    private String cognitoUsername;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}
