package com.example.online_library.book;

import com.example.online_library.book.dto.BookCreateDto;
import com.example.online_library.book.dto.BookResponseDto;
import com.example.online_library.book.entity.Book;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;
    @Transactional
    public List<BookResponseDto> getAllBooks() {
        List<Book> all = bookRepository.findAll();
        return bookDtoMapper.toResponseDto(all);
    }
    @Transactional
    public BookResponseDto getBookById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow();
        return bookDtoMapper.toResponseDto(book);
    }
    @Transactional
    public void create(BookCreateDto createDto) {
        Book entity = bookDtoMapper.toEntity(createDto);
        bookRepository.save(entity);
    }
    @Transactional
    public void update(Integer id, BookCreateDto dto) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow();
        book.setName(dto.getName());
        book.setAuthor(dto.getAuthor());
    }
}
