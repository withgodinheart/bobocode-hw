package com.bobocode.nasafeign.controller;

import com.bobocode.nasafeign.dto.ResponseDto;
import com.bobocode.nasafeign.service.NasaServiceRecords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mars")
public class NasaController {

    private final NasaServiceRecords nasaService;

    @GetMapping(value = "/{sol}")
    public ResponseEntity<?> get(@PathVariable Integer sol, @RequestParam(required = false) String camera) {
        return nasaService.run(sol, camera);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> error(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto("failed", "No pictures found"));
    }
}
