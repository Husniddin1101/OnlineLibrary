package com.example.online_library.book;

import com.example.online_library.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book , Integer> {
    Optional<Book> findById(Integer id);

    Optional<Book> findByName(String name);
}
