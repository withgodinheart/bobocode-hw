package com.bobocode.creditadvisory.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications", indexes = {
        @Index(name = "applications_applicant_id_idx", columnList = "applicant_id"),
        @Index(name = "applications_advisor_id_idx", columnList = "advisor_id")
})
@ToString(exclude = {"applicant", "advisor"})
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(columnDefinition = "int default 1")
    private Integer version;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    private LocalDateTime assignedAt;

    private LocalDateTime resolvedAt;

    //    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id")
    private Advisor applicant;

    //    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "advisor_id")
    private Advisor advisor;

    public enum Status {
        NEW, ASSIGNED, ON_HOLD, APPROVED, CANCELED, DECLINED
    }

    public void changeToAssigned() {
        this.status = Status.ASSIGNED;
        this.setAssignedAt(LocalDateTime.now());
    }
}

