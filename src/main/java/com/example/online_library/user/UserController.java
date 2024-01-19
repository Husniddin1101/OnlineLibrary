package com.example.online_library.user;

import com.example.online_library.book.dto.BookCreateDto;
import com.example.online_library.user.dto.PhoneAndPasswordDto;
import com.example.online_library.user.dto.UserCreateDto;
import com.example.online_library.role.RoleService;
import com.example.online_library.user.dto.UserUpdateDto;
import com.example.online_library.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @GetMapping("/sign-in")
    public String signIn() {
        return "user/sign-in";
    }

    @GetMapping("/user/create")
    public String signUp(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "user/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserCreateDto userCrateDTO ) throws IOException {
        userService.create(userCrateDTO);
        return "redirect:user/sign-up";
    }

    @GetMapping("/phoneNumber")
    public String phoneNumber() {
        return "/user/phoneNumber";
    }

    @PostMapping("/phoneNumber")
    public String phoneNumber(@ModelAttribute PhoneAndPasswordDto dto) {
        userService.findByPhoneNumberAndUpdate(dto);
        return "redirect:/login";
    }

    @GetMapping("/user/profile/{id}")
    public String getProfile(@PathVariable UUID id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("userPic", "/imageP/" + user.getPictureName());
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/user/{id}")
    public String updateUser(@PathVariable UUID id , @ModelAttribute UserUpdateDto dto) {
        userService.update(id ,dto);
        return "user/sign-in";
    }
}
