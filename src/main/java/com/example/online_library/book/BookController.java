package com.example.online_library.book;

import com.example.online_library.book.dto.BookCreateDto;
import com.example.online_library.book.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PreAuthorize(value = "hasAuthority('BOOK_CREATE')")
    @PostMapping
    public String createBook(@ModelAttribute BookCreateDto createDto) {
        bookService.create(createDto);
        return "index";
    }

    @GetMapping("/create")
    public String getCreatePage(){
        return "book/create";
    }

    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @GetMapping
    public String getAllBooks(Model model) {
        List<BookResponseDto> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        return "index";
    }

    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @GetMapping("/{id}")
    public String getBookId(@PathVariable Integer id, Model model) {
        bookService.getBookById(id);
        return "book/books";
    }

    @GetMapping("/{id}/update")
    public String getEditBook(@PathVariable Integer id , Model model) {
        BookResponseDto bookResponseDto = bookService.getBookById(id);
        model.addAttribute("book" , bookResponseDto);
        return "book/update";
    }

    @PreAuthorize(value = "hasAuthority('BOOK_EDIT')")
    @PutMapping("/{id}")
    public String updateBook(@PathVariable Integer id , @ModelAttribute BookCreateDto dto){
        bookService.update(id, dto);
        return "redirect:/book";
    }
}
