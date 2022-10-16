package com.bobocode.rabbitnasa.storage;

import java.util.Optional;

public interface Storage {

    void put(String key, byte[] content);

    Optional<byte[]> get(String key);
}
