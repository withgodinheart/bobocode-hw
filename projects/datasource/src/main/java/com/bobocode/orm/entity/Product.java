package com.bobocode.orm.entity;

import com.bobocode.orm.annotation.Column;
import com.bobocode.orm.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Table("products")
@Data
public class Product {

    @Column("id")
    private long id;
    @Column("name")
    private String name;
    @Column("price")
    private BigDecimal price;
    @Column("create_at")
    private Timestamp createdAt;
    @Column("owner_name")
    private String ownerName;
}
