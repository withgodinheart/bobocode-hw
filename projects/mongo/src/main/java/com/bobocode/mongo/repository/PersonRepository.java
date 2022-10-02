package com.bobocode.mongo.repository;

import com.bobocode.mongo.document.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, Integer> {
}
