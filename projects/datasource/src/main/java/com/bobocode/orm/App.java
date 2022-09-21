package com.bobocode.orm;

import com.bobocode.orm.entity.Product;
import com.bobocode.orm.session.SessionFactory;
import com.bobocode.pool.DataSourcePool;

import javax.sql.DataSource;

public class App {

    public static void main(String[] args) {
        var sf = new SessionFactory(initializePooledDataSource());
        var session = sf.createSession();
        var product = session.find(Product.class, 1L);
        System.out.println(product);
        var sameProduct = session.find(Product.class, 1L);
        System.out.println(sameProduct);

        System.out.println(product == sameProduct);
    }

    private static DataSource initializePooledDataSource() {
        return new DataSourcePool(
                "jdbc:postgresql://localhost:5432/postgres",
                "C5320729",
                "",
                5);
    }
}
