package com.bobocode.hw25;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;

public class LongestProduct {

    public static final String QUERY = """
            SELECT *
            FROM products
            WHERE length(name) = (SELECT MAX(length(name)) FROM products)
            """;

    @SneakyThrows
    public static void main(String[] args) {
        var set = new HashSet<NamePrice>();
        try (var ds = getDataSource().getConnection()) {
            try (var st = ds.createStatement()) {
                var rs = st.executeQuery(QUERY);
                while (rs.next()) {
                    set.add(new NamePrice(rs.getString("name"), rs.getBigDecimal("price")));
                }
            }
        }

        set.stream().max(Comparator.comparing(NamePrice::price)).ifPresent(System.out::println);
    }

    private record NamePrice(String name, BigDecimal price) {
    }

    @SneakyThrows
    private static DataSource getDataSource() {
        var dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("C5320729");
        dataSource.setPassword("");
        dataSource.setDatabaseName("postgres");

        return dataSource;
    }
}
