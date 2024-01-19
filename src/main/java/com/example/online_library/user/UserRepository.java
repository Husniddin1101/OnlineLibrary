package com.example.online_library.user;

import com.example.online_library.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.phoneNumber = :phoneNumber")
    void findByPhoneNumberAndUpdate(String phoneNumber, String password);

    User findByName(String name);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
