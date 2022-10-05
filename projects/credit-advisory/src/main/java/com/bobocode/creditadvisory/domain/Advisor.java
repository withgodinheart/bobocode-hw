package com.bobocode.creditadvisory.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "advisors")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Advisor extends User {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "advisor")
    private List<Application> applications;

    @RequiredArgsConstructor
    public enum Role {
        ASSOCIATE(BigDecimal.ZERO, BigDecimal.valueOf(9_999)),
        PARTNER(BigDecimal.valueOf(10_000), BigDecimal.valueOf(49_999)),
        SENIOR(BigDecimal.valueOf(50_000), BigDecimal.valueOf(Integer.MAX_VALUE));

        @Getter
        private final BigDecimal from;
        @Getter
        private final BigDecimal to;
    }

    public boolean hasAssignedApplication() {
        return this.applications.stream()
                .anyMatch(a -> a.getStatus().equals(Application.Status.ASSIGNED));
    }

    public void addApplication(Application application) {
        this.applications.add(application);
        application.setAdvisor(this);
    }

    public void removeApplication(Application application) {
        this.applications.remove(application);
        application.setAdvisor(null);
    }
}
