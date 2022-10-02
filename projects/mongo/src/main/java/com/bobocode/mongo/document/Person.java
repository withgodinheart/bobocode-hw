package com.bobocode.mongo.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "person")
public class Person {

    @Id
    private ObjectId id;

    private String name;

    private Integer age;

    private List<String> friends;
}
