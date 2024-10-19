package com.eca.crud.example.service;

import com.eca.crud.example.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final List<User> users = new ArrayList<>();

    static {
        // Add static users (username, password, role)
        users.add(new User("user1", "password1", "ROLE_USER"));
        users.add(new User("admin", "password2", "ROLE_ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Create UserDetails object with plain text password
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword()) // Use plain text password
                .roles(user.getRole().split("_")) // Split by underscore if multiple roles
                .build();
    }
}
