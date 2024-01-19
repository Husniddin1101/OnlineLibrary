package com.example.online_library.cart;


import com.example.online_library.book.entity.Book;
import com.example.online_library.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface CartRepository extends JpaRepository<Cart , UUID> {
    @Query("""
            SELECT b
            FROM Cart c
            LEFT JOIN Book b ON c.book.id = b.id
            WHERE c.user.id = :id
            """)
    List<Book> getBooks(@Param("id") UUID userId);}
