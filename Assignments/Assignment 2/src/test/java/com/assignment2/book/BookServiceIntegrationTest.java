package com.assignment2.book;

import com.assignment2.TestCreationFactory;
import com.assignment2.book.model.Book;
import com.assignment2.book.model.dto.BookDTO;
import com.assignment2.book.repository.BookRepository;
import com.assignment2.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        List<Book> items = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(items);
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        Assertions.assertNotNull(bookService.create(bookDTO));
    }

    @Test
    void delete(){
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        bookDTO = bookService.create(bookDTO);
        bookService.delete(bookDTO.getId());
    }

    @Test
    void edit(){
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        bookDTO = bookService.create(bookDTO);
        Assertions.assertEquals(bookDTO.getId(), bookService.edit(bookDTO.getId(), bookDTO).getId());
    }

    @Test
    void sell() {
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        bookDTO = bookService.create(bookDTO);
        Assertions.assertTrue(bookService.sell(bookDTO.getId(), 1));
    }
}
