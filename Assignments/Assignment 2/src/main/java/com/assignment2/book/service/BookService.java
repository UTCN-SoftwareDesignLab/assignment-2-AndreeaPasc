package com.assignment2.book.service;

import com.assignment2.book.mapper.BookMapper;
import com.assignment2.book.model.Book;
import com.assignment2.book.model.dto.BookDTO;
import com.assignment2.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The book was not found: " + id));
    }

    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDTO(bookRepository.save(
                bookMapper.fromDTO(book)
        ));
    }

    public BookDTO edit(Long id, BookDTO book) {
        Book actBook = findById(id);
        actBook.setId(book.getId());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        actBook.setPrice(book.getPrice());
        return bookMapper.toDTO(
                bookRepository.save(actBook)
        );
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public boolean sell(Long id, int quantity){
        Book actBook = findById(id);
        if(actBook.getQuantity() - quantity >= 0){
            actBook.setQuantity(actBook.getQuantity() - quantity);
            bookRepository.save(actBook);
            return true;
        }else return false;
    }
}
