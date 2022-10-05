package com.bobocode.creditadvisory.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "applicants")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Applicant extends User {

    @NaturalId
    @Column(nullable = false, unique = true)
    private String ssn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "applicant")
    private List<Application> applications;

    @ElementCollection
    @CollectionTable(name = "applicant_phones", joinColumns = @JoinColumn(name = "applicant_id"),
            indexes = @Index(name = "applicant_phones_applicant_id_idx", columnList = "applicant_id"))
    private List<PhoneNumber> phoneNumbers;

    @Embedded
    private Address address;

    enum Role {
        ASSOCIATE, PARTNER, SENIOR
    }
}
