package com.bobocode.controller;

import com.bobocode.repository.BookRepository;
import com.bobocode.service.FillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final BookRepository bookRepository;
    private final FillService fillService;

    @GetMapping("/{id}/fill")
    private void fill(@PathVariable Long id) {
        fillService.fillBooks(id);
    }

    @GetMapping("/{id}/books")
    private ResponseEntity<?> getBooks(@PathVariable Long id) {
        var books = bookRepository.findAllByAuthorId(id);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}/books/dto")
    private ResponseEntity<?> getBooksDto(@PathVariable Long id) {
        var books = bookRepository.customMethod(id);
        return ResponseEntity.ok(books);
    }
}
