package com.bobocode.pool;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.function.Supplier;

public class App {

    public static void main(String[] args) {
        System.out.println("Pool:");
        benchmark(App::initializePooledDataSource);
        System.out.println("===========");
        System.out.println("Simple:");
        benchmark(App::initializeSimpleDataSource);
    }

    @SneakyThrows
    private static void benchmark(Supplier<DataSource> supplier) {
        var total = 0d;
        var start = System.nanoTime();
        var dataSource = supplier.get();

        for (int i = 0; i < 500; i++) {
            try (var connection = dataSource.getConnection()) {
                connection.setAutoCommit(false);
                try (var statement = connection.createStatement()) {
                    var resultSet = statement.executeQuery("SELECT random() from products");
                    resultSet.next();
                    total += resultSet.getDouble(1);
                }
                connection.rollback();
            }
        }

        System.out.println("Execution time: " + ((System.nanoTime() - start) / 1_000_000) + "ms");
        System.out.println("Total: " + total);
    }

    private static DataSource initializePooledDataSource() {
        return new DataSourcePool(
                "jdbc:postgresql://localhost:5432/postgres",
                "C5320729",
                "",
                5);
    }

    private static DataSource initializeSimpleDataSource() {
        var dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("C5320729");
        dataSource.setPassword("");

        return dataSource;
    }
}
