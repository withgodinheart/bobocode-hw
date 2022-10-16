package com.bobocode.rabbitnasa.util;

import com.bobocode.rabbitnasa.dto.RequestDto;
import com.bobocode.rabbitnasa.exception.InvalidCommandException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ValidationUtil {

    public static void validateCommand(RequestDto request) {
        if (Objects.isNull(request.getSol())) {
            throw new InvalidCommandException("Command is Invalid");
        }
    }

    public static void throwRandomRuntimeException() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            log.warn("******* Random exception will be thrown");
            throw new RuntimeException();
        }
    }
}
