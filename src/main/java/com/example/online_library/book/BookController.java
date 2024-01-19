package com.example.online_library.book;

import com.example.online_library.book.dto.BookCreateDto;
import com.example.online_library.book.dto.BookResponseDto;
import com.example.online_library.book.entity.Book;
import com.example.online_library.cart.CartService;
import com.example.online_library.user.UserRepository;
import com.example.online_library.user.entity.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartService cartService;

    @PreAuthorize(value = "hasAuthority('BOOK_CREATE')")
    @PostMapping("/book")
    public String createBook(@RequestParam("name") String name,
                             @RequestParam("author") String author,
                             @RequestParam("file") MultipartFile file
    ) throws IOException {
        bookService.create(new BookCreateDto(name, author, file));
        return "redirect:/";
    }

    @GetMapping("/book/create")
    public String getCreatePage(){
        return "book/create";
    }

    @GetMapping()
    public String getAllBooks(Model model,Authentication authentication) {
        if (authentication==null){
        List<BookResponseDto> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        }else {
            List<BookResponseDto> allBooks = bookService.getAllBooks();
            model.addAttribute("books", allBooks);
            User principal = (User) authentication.getPrincipal();
            model.addAttribute("user", principal.getId());
            model.addAttribute("userPic", "/imageP/" + principal.getPictureName());
        }
            return "index";
    }

    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @GetMapping("/book/{id}")
    public String getBookId(@PathVariable Integer id, Model model) {
        BookResponseDto bookById = bookService.getBookById(id);
        model.addAttribute("bookById", bookById);
        model.addAttribute("bookFile","/files/"+bookById.getFile());
        return "book/books";
    }

    @GetMapping("/book/{id}/update")
    public String getEditBook(@PathVariable Integer id , Model model) {
        BookResponseDto bookResponseDto = bookService.getBookById(id);
        model.addAttribute("book" , bookResponseDto);
        return "book/update";
    }

    @PreAuthorize(value = "hasAuthority('BOOK_EDIT')")
    @PutMapping("/book/{id}")
    public String updateBook(@PathVariable Integer id , @ModelAttribute BookCreateDto dto){
        bookService.update(id, dto);
        return "redirect:/book";
    }
    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @PostMapping("/book/addToCart/{bookId}")
    public String addToCart(@PathVariable Integer bookId , Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        User user = userRepository.findUserByEmail(principal.getEmail()).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (user != null && book != null) {
            cartService.addToCart(user, book);
        }
        return "redirect:/";
    }

    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @GetMapping("/search")
    public String searchByName(@RequestParam("name") String name, Model model) {
        List<BookResponseDto> byName = bookService.findByName(name);
        model.addAttribute("books", byName);
        return "index" ;
    }

    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @GetMapping("/downloadFile/{id}")
    public @ResponseBody Resource downloadFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        return bookService.downloadFile(id, response);
    }
}
