package com.example.online_library.authorization;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/test")
public class AuthorizationTestController {
    @PreAuthorize(value = "hasAuthority('BOOK_CREATE')")
    @GetMapping("/book_create")
    public String bookCreate(){
        return "book_create";
    }
    @PreAuthorize(value = "hasAuthority('BOOK_EDIT')")
    @GetMapping("/book_edit")
    public String bookEdit(){
        return "book_edit";
    }
    @PreAuthorize(value = "hasAuthority('BOOK_READ')")
    @GetMapping("/book_read")
    public String bookRead(){
        return "book_read";
    }
}
