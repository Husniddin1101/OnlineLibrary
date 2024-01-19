package com.example.online_library.user;

import com.example.online_library.user.dto.PhoneAndPasswordDto;
import com.example.online_library.user.dto.UserCreateDto;
import com.example.online_library.user.dto.UserUpdateDto;
import com.example.online_library.user.entity.User;
import com.example.online_library.role.RoleRepository;
import com.example.online_library.role.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.authorization.AuthoritiesAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Transactional
    public void create(UserCreateDto userCrateDTO) throws IOException {
        MultipartFile picture = userCrateDTO.getPicture();
        Path path = Path.of("src/main/resources/static/imageP/"+picture.getOriginalFilename());
        Files.write(path,picture.getBytes());
        Role role = roleRepository.findByName(userCrateDTO.getRole()).orElseThrow();
        User user = new User(
                null,
                userCrateDTO.getName(),
                userCrateDTO.getSurname(),
                userCrateDTO.getEmail(),
                userCrateDTO.getPhoneNumber(),
                userCrateDTO.getPassword(),
                userCrateDTO.getPicture().getOriginalFilename(),
                role,
                new ArrayList<>()
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow();
    }
    @Transactional
    public void findByPhoneNumberAndUpdate(PhoneAndPasswordDto dto) {
        User user = userRepository.findByPhoneNumber(dto.getPhoneNumber()).orElseThrow();
        user.setPassword(dto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @Transactional
    public void update(UUID id, UserUpdateDto updateDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setId(id);
        user.setCarts(user.getCarts());
        user.setRole(user.getRole());
        user.setPictureName(user.getPictureName());
        user.setName(updateDto.getName());
        user.setSurname(updateDto.getSurname());
        user.setEmail(updateDto.getEmail());
        user.setPassword(updateDto.getPassword());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }
}
