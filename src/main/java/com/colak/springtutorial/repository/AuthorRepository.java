package com.colak.springtutorial.repository;

import com.colak.springtutorial.jpa.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Use JPQL
    // select a.* from author a where a.id=?
    // select b.* from book b where b.author_id=?
    @Query("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = :authorId")
    Optional<Author> findByIdWithBooks(@Param("authorId") Long authorId);

    // select a.*,b.* from author a join book b on a.id=b.author_id
    @Query("SELECT a FROM Author a JOIN FETCH a.books")
    List<Author> findAllWithBooks();

}
