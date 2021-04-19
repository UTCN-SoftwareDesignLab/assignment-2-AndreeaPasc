package com.assignment2.book;

import com.assignment2.TestCreationFactory;
import com.assignment2.book.mapper.BookMapper;
import com.assignment2.book.model.Book;
import com.assignment2.book.model.dto.BookDTO;
import com.assignment2.book.repository.BookRepository;
import com.assignment2.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        Book book = Book.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookMapper.fromDTO(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        BookDTO newBook = bookService.create(bookDTO);
        Assertions.assertNotNull(newBook);
    }

    @Test
    void update(){
        BookDTO bookDTO = BookDTO.builder()
                .id(6L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        Book book = Book.builder()
                .id(6L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        when(bookMapper.fromDTO(bookDTO)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Assertions.assertEquals(bookDTO.getId(), bookService.edit(bookDTO.getId(), bookDTO).getId());
    }

    @Test
    void delete(){
        Book book = Book.builder()
                .id(6L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        bookService.delete(book.getId());
    }

    @Test
    void sell(){
        BookDTO bookDTO = BookDTO.builder()
                .id(6L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        Book book = Book.builder()
                .id(6L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        when(bookMapper.fromDTO(bookDTO)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Assertions.assertEquals(true, bookService.sell(bookDTO.getId(), 1));
    }
}
