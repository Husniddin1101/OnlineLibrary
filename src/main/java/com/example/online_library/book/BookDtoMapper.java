package com.example.online_library.book;

import com.example.online_library.book.dto.BookCreateDto;
import com.example.online_library.book.dto.BookResponseDto;
import com.example.online_library.book.entity.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookDtoMapper {
    private final ModelMapper modelMapper;

    public BookResponseDto toResponseDto(Book book){
        return modelMapper.map(book,BookResponseDto.class);
    }

    public List<BookResponseDto> toResponseDto(List<Book> books){
        return books.stream()
                .map(this::toResponseDto)
                .toList();
    }

    public Book toEntity(BookCreateDto bookCreateDto){
        return modelMapper.map(bookCreateDto , Book.class);
    }
}
