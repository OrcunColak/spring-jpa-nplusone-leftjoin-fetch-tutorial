package com.colak.springtutorial.repository;

import com.colak.springtutorial.jpa.Author;
import com.colak.springtutorial.jpa.Book;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This repository uses EntityManager API to solve N+1 Select Problem
 */
@Repository
@RequiredArgsConstructor
public class AuthorRepository2 {

    private final EntityManager entityManager;

    // See https://medium.com/jpa-java-persistence-api-guide/hibernate-lazyinitializationexception-solutions-7b32bfc0ce98
    // Use JPQL + EntityManager
    public Author findByIdWithBooks(@Param("authorId") Long authorId) {
        TypedQuery<Author> query = entityManager.createQuery(
                "SELECT a FROM Author a LEFT JOIN FETCH a.books WHERE a.id = :authorId", Author.class);
        query.setParameter("authorId", authorId);
        return query.getSingleResult();
    }

    public List<Author> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Author> criteria = builder.createQuery(Author.class);

        // Define the root for the Author entity
        Root<Author> root = criteria.from(Author.class);

        // Perform a left join with the Book entity
        Fetch<Author, Book> personFetch = root.fetch("books", JoinType.LEFT);

        TypedQuery<Author> typedQuery = entityManager.createQuery(criteria);
        return typedQuery.getResultList();
    }

}
