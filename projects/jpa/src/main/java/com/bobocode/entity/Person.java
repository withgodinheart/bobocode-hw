package com.bobocode.entity;

import com.bobocode.enums.TeamEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "persons")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    private List<Note> notes;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TeamEnum team;
}
