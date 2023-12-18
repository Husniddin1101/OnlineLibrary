package com.example.online_library.role;

import com.example.online_library.role.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping
    public String getRolePage(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "role/roles";
    }
    @GetMapping("/{id}")
    public String getRole(@PathVariable Integer id , Model model) {
        Role role = roleService.getRole(id);
        model.addAttribute("role", role);
        return "role/role";
    }

//    @PostMapping
//    public String createRole(@ModelAttribute RoleCreateDto roleCreateDto) {
//        roleService.create(roleCreateDto);
//        return "redirect:/role";
//    }

    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable Integer id) {
        roleService.delete(id);
        return "redirect:/role";
    }
}
