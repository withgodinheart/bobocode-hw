package com.bobocode.nasa.controller;

import com.bobocode.nasa.service.NasaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/nasa")
@RequiredArgsConstructor
@Slf4j
public class NasaController {

    private final NasaService nasaService;

    @GetMapping(value = "/{sol}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] get(@PathVariable Integer sol, @RequestParam("camera") Optional<String> camera) {
        log.info("******* PNG ENDPOINT");
        return nasaService.run(sol, camera);
    }

    @GetMapping(value = "/{sol}")
    public ResponseEntity<?> getUri(@PathVariable Integer sol, @RequestParam("camera") Optional<String> camera) {
        log.info("******* RESPONSE_ENTITY ENDPOINT");
        var uri = nasaService.getUri(sol, camera);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(uri).build();
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleException() {
        return ResponseEntity.notFound().build();
    }
}
