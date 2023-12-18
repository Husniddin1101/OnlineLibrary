package com.example.online_library.role.entity;

import com.example.online_library.permission.Permission;
import com.example.online_library.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true , nullable = false)
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "role")
    private List<User> users;

    @Enumerated(EnumType.STRING)
    private List<Permission> permission;
}
