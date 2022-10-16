package com.bobocode.rabbitnasa.storage;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StorageImpl implements Storage {

    private final Map<String, byte[]> storage = new ConcurrentHashMap<>();

    @Override
    public void put(String key, byte[] content) {
        storage.put(key, content);
    }

    @Override
    public Optional<byte[]> get(String key) {
        return Optional.of(storage.get(key));
    }
}
