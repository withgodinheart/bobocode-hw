package com.bobocode;

import com.bobocode.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        var emf = Persistence.createEntityManagerFactory("default");
        var em = emf.createEntityManager();
//        getProduct(em);
//        insertProduct(em);
        isEqual(em);
        em.close();
    }

    private static void isEqual(EntityManager em) {
        Product product = em.find(Product.class, 1L);
        Product product1 = em.find(Product.class, 1L);
        Product product2 = em.createQuery("SELECT p FROM Product p WHERE p.id = 1", Product.class).getSingleResult();
        System.out.println(product == product1);
        System.out.println(product == product2);

    }

    private static void getProduct(EntityManager em) {
        em.createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class)
                .setParameter("id", 1)
                .getResultStream()
                .forEach(System.out::println);
    }

    private static void insertProduct(EntityManager em) {
        var fruit = new Product();
        fruit.setName("Natural fruit");
        fruit.setPrice(BigDecimal.valueOf(100L));

        em.getTransaction().begin();
        em.persist(fruit);
        em.getTransaction().commit();

        em.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", "Natural fruit")
                .getResultStream()
                .forEach(System.out::println);
    }
}
