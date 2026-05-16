package com.aanuj.spring.app.SpringApplication.utils;

import com.aanuj.spring.app.SpringApplication.entities.Enum.Permission;
import com.aanuj.spring.app.SpringApplication.entities.Enum.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aanuj.spring.app.SpringApplication.entities.Enum.Permission.*;
import static com.aanuj.spring.app.SpringApplication.entities.Enum.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, POST_VIEW),
            CREATOR, Set.of(USER_VIEW, POST_VIEW, USER_UPDATE, POST_UPDATE),
            ADMIN, Set.of(USER_CREATE, POST_CREATE, USER_VIEW, POST_VIEW, USER_UPDATE, POST_UPDATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return map.get(role).stream().map(permission ->
            new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
    }
}
