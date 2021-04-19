package com.assignment2.book.mapper;

import com.assignment2.book.model.Book;
import com.assignment2.book.model.dto.BookDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-19T23:15:17+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.3.jar, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setId( book.getId() );
        bookDTO.setTitle( book.getTitle() );
        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setGenre( book.getGenre() );
        bookDTO.setQuantity( book.getQuantity() );
        bookDTO.setPrice( book.getPrice() );

        return bookDTO;
    }

    @Override
    public Book fromDTO(BookDTO book) {
        if ( book == null ) {
            return null;
        }

        Book book1 = new Book();

        book1.setId( book.getId() );
        book1.setTitle( book.getTitle() );
        book1.setAuthor( book.getAuthor() );
        book1.setGenre( book.getGenre() );
        book1.setQuantity( book.getQuantity() );
        book1.setPrice( book.getPrice() );

        return book1;
    }
}
