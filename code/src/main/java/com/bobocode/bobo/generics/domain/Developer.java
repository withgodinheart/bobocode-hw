package com.bobocode.bobo.generics.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Developer extends Employee {

    protected SENIORITY seniority;

    public enum SENIORITY {
        JUNIOR, MIDDLE, SENIOR, LEAD
    }
}
