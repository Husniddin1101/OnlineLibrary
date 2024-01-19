package com.example.online_library.book.dto;

import com.example.online_library.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDto {
    private Integer id;
    private String name;
    private String author;
    private MultipartFile file;
    private List<Cart> carts;
}
