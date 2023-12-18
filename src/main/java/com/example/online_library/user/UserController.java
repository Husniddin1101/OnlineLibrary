package com.example.online_library.user;

import com.example.online_library.user.dto.PhoneAndPasswordDto;
import com.example.online_library.user.dto.UserCreateDto;
import com.example.online_library.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String signUp(@ModelAttribute UserCreateDto userCrateDTO ) {
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

}
