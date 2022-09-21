package com.bobocode.checkfetch;

import com.bobocode.checkfetch.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

import java.util.function.Function;

public class App {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
        doInTx(em -> {
            var person = em.createQuery("select p from Person p left join fetch p.notes where p.id = 3", Person.class)
                    .getSingleResult();
            System.out.println(person);
            System.out.println(person.getNotes());
            return null;
        });
    }

    private static <T> T doInTx(Function<EntityManager, T> fnc) {
        var em = emf.createEntityManager();
        em.unwrap(Session.class).setDefaultReadOnly(true);
        em.getTransaction().begin();
        try {
            var result = fnc.apply(em);
            em.getTransaction().commit();
            return result;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error performing query. Transaction is rolled back", e);
        } finally {
            em.close();
        }
    }
}
