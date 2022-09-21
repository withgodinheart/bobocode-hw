package com.bobocode.hw21.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cameras {

    @Id
    long id;
    long nasa_id;
    String name;
}
