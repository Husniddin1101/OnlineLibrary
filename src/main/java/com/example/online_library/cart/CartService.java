package com.example.online_library.cart;

import com.example.online_library.book.entity.Book;
import com.example.online_library.cart.entity.Cart;
import com.example.online_library.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void addToCart(User user, Book book) {
        Cart cart = new Cart(UUID.randomUUID(), user, book);
        cartRepository.save(cart);
    }

    public List<Book> getBooks(UUID userId) {

        return cartRepository.getBooks(userId);
    }
}
