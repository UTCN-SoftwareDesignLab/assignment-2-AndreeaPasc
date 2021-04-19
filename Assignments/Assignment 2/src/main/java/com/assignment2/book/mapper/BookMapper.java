package com.assignment2.book.mapper;

import com.assignment2.book.model.Book;
import com.assignment2.book.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(Book book);

    Book fromDTO(BookDTO book);
}
