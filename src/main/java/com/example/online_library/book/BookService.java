package com.example.online_library.book;

import com.example.online_library.book.dto.BookCreateDto;
import com.example.online_library.book.dto.BookResponseDto;
import com.example.online_library.book.entity.Book;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void create(BookCreateDto createDto) throws IOException {
        MultipartFile file = createDto.getFile();
        Path path = Path.of("src/main/resources/static/files/"+file.getOriginalFilename());
        Files.write(path, file.getBytes());
        Book entity = new Book(
                null,
                createDto.getName(),
                createDto.getAuthor(),
                createDto.getFile().getOriginalFilename(),
                new ArrayList<>()
        );
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

    public List<BookResponseDto> findByName(String name) {
        Book book = bookRepository.findByName(name).orElseThrow();
        List<Book> all = new ArrayList<>();
        all.add(book);
        return bookDtoMapper.toResponseDto(all);
    }

    public Resource downloadFile(Integer id, HttpServletResponse response) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow();
        Path path = Path.of("src/main/resources/static/files/"+book.getFilePath());
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
