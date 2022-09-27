package com.bobocode.repository;

import com.bobocode.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @QueryHints(@QueryHint(name = HINT_FETCH_SIZE, value = "1"))
    @Query("select p from Person p left join fetch p.notes")
    Stream<Person> streamAllBy();

    @Query("select p from Person p where p.id = :id")
    Person myMethod(Long id);
}
