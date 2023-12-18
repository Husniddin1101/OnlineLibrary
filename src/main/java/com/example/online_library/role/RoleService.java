package com.example.online_library.role;

import com.example.online_library.role.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
//    @Transactional
//    public void create(RoleCreateDto roleCreateDto) {
//        Role role = new Role(null, roleCreateDto.getName(), Collections.emptySet());
//        roleRepository.save(role);
//    }

    @Transactional
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    public Role getRole(Integer id) {
          return roleRepository.findById(id)
                .orElseThrow();
    }

}
