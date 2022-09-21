package com.bobocode;

import com.bobocode.entity.Product;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class DeadLock {

    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("default");
        var em1 = emf.createEntityManager();
        var em2 = emf.createEntityManager();
        em1.getTransaction().begin();
        em2.getTransaction().begin();

        em1.persist(getProduct(1L, 5L));
        em2.persist(getProduct(2L, 10L));
        em1.persist(getProduct(2L, 4L));
        em2.persist(getProduct(1L, 11L));

        em1.getTransaction().commit();
        em2.getTransaction().commit();

        em1.close();
        em2.close();
    }

    private static Product getProduct(long id, long price) {
        var product = new Product();
        product.setId(id);
        product.setPrice(BigDecimal.valueOf(price));

        return product;
    }
}
