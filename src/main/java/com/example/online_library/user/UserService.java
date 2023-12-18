package com.example.online_library.user;

import com.example.online_library.user.dto.PhoneAndPasswordDto;
import com.example.online_library.user.dto.UserCreateDto;
import com.example.online_library.user.entity.User;
import com.example.online_library.role.RoleRepository;
import com.example.online_library.role.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Transactional
    public void create(UserCreateDto userCrateDTO) {
        Role role = roleRepository.findByName(userCrateDTO.getRole()).orElseThrow();
        User user = new User(
                null,
                userCrateDTO.getName(),
                userCrateDTO.getSurname(),
                userCrateDTO.getEmail(),
                userCrateDTO.getPhoneNumber(),
                userCrateDTO.getPassword(),
                role
                );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username).orElseThrow();
    }

    public void findByPhoneNumberAndUpdate(PhoneAndPasswordDto dto) {
        userRepository.findByPhoneNumberAndUpdate(dto.getPhoneNumber(), dto.getPassword());
    }
}
