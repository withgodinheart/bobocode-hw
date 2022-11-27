package com.bobocode.service;

import com.bobocode.entity.Book;
import com.bobocode.repository.AuthorRepository;
import com.bobocode.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class FillService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public void fillBooks(Long authorId) {
        var author = authorRepository.findById(authorId).orElseThrow();

        var listOfBooks = new ArrayList<Book>();
        for (int i = 0; i < 5; i++) {
            var book = Book.builder().name("Book #" + i).author(author).build();
            listOfBooks.add(book);
        }

        bookRepository.saveAll(listOfBooks);
    }
    // Hibernate: select book0_.id as id1_1_, book0_.author_id as author_i3_1_, book0_.name as name2_1_ from books book0_ inner join authors author1_ on book0_.author_id=author1_.id where author1_.id=?
    // Hibernate: select book0_.id as id1_1_, book0_.author_id as author_i3_1_, book0_.name as name2_1_ from books book0_ where book0_.author_id=?

}
