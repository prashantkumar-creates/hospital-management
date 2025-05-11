package com.hospital.Service;


import com.hospital.Entity.Role;
import com.hospital.Entity.User;
import com.hospital.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

// UserService.java
@Service
public class UserService implements UserDetailsService   {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList()));
    }
    public User registerNewUser(User user) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUserRoles(Long userId, Set<Role> newRoles) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRoles(newRoles);
        return userRepository.save(user);
    }
}

