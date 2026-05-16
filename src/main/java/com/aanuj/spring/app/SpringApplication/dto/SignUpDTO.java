package com.aanuj.spring.app.SpringApplication.dto;

import com.aanuj.spring.app.SpringApplication.entities.Enum.Permission;
import com.aanuj.spring.app.SpringApplication.entities.Enum.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}
