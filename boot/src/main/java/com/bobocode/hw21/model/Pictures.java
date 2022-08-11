package com.bobocode.hw21.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pictures {

    @Id
    long id;
    long nasa_id;
    String img_src;
    long camera_id;
}
