package com.colak.springtutorial.repository;

import com.colak.springtutorial.jpa.Author;
import com.colak.springtutorial.jpa.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Slf4j
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    @Transactional
    void findById() {
        Optional<Author> optional = repository.findById(1L);
        optional.ifPresentOrElse(author -> {
                    for (Book book : author.getBooks()) {
                        log.info("Book title : {}", book.getTitle());
                    }
                },
                () -> Assertions.fail("Test failed")
        );
    }

    @Test
    @Transactional
    void findByIdWithBooks() {
        Optional<Author> optional = repository.findByIdWithBooks(1L);
        optional.ifPresentOrElse(author -> {
                    for (Book book : author.getBooks()) {
                        log.info("Book title : {}", book.getTitle());
                    }
                },
                () -> Assertions.fail("Test failed")
        );
    }
}
