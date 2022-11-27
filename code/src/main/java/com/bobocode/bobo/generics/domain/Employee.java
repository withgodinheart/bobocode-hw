package com.bobocode.bobo.generics.domain;

import lombok.Data;

@Data
public class Employee implements Comparable<Employee> {
    protected Long employeeNumber;

    @Override
    public int compareTo(Employee o) {
        return employeeNumber.compareTo(o.employeeNumber);
    }
}
