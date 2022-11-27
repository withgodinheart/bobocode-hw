package com.bobocode.repository;

import com.bobocode.dto.BookDto;
import com.bobocode.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.author.id = :authorId")
    List<Book> findAllByAuthorId(Long authorId);

    @Query("select new com.bobocode.dto.BookDto(b.name, a.name) from Book b left join b.author a where a.id = :authorId")
    List<BookDto> customMethod(Long authorId);
}
