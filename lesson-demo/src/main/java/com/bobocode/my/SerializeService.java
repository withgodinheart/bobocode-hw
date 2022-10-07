package com.bobocode.my;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeService {

    private final ObjectMapper om = new ObjectMapper();

    public static void main(String[] args) {
        new SerializeService().serialize(new Person(1L, "Alex", 20));
        new SerializeService().read(Person.class);

        new SerializeService().toJson(new Person(2L, "Zhenya", 22));
        new SerializeService().fromJson(Person.class);
    }

    record Person(long id, String name, int age) implements Serializable {
    }

    @SneakyThrows
    public void serialize(Object person) {
        try (var fs = new FileOutputStream("log.txt", false);
             var oos = new ObjectOutputStream(fs)) {
            oos.writeObject(person);
        }
    }

    @SneakyThrows
    public <T> T read(Class<T> type) {
        try (var fs = new FileInputStream("log.txt");
             var oos = new ObjectInputStream(fs)) {
            var object = type.cast(oos.readObject());
            System.out.println(object);
            return object;
        }
    }

    @SneakyThrows
    public void toJson(Person person) {
        var s = om.writeValueAsString(person);
        serialize(s);
    }

    @SneakyThrows
    public <T> T fromJson(Class<T> type) {
        var string = read(String.class);
        var object = om.readValue(string, type);
        System.out.println(object);
        return object;
    }
}
