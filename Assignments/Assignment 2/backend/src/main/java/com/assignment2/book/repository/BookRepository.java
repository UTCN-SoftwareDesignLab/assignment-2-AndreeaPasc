package com.assignment2.book.repository;

import com.assignment2.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingAndAuthorContainingAndGenreContainingIgnoreCase(String title, String author, String genre);

}
