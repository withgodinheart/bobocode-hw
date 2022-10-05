package com.bobocode.creditadvisory.controller;

import com.bobocode.creditadvisory.dto.ErrorDto;
import com.bobocode.creditadvisory.dto.SuccessDto;
import com.bobocode.creditadvisory.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advisors")
// TASK HERE: https://docs.google.com/document/d/16SSfMnGR25fK8_-Irt1qPo6bPmmgUAwClgvvGOkEAU4/edit
public class AppController {

    private final AppService appService;

    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assign(@PathVariable Long id) {
        var application = appService.assign(id);

        return ResponseEntity.ok(new SuccessDto(application.getId()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handle(RuntimeException e) {
        return ResponseEntity.ok(new ErrorDto("error", e.getMessage()));
    }
}
