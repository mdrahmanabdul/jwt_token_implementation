package com.jwtpr.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtpr.repo.UserRepo;

@Service
public class UserService implements UserDetailsService { // ✅ Implement UserDetailsService

    @Autowired
    private UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                .map(user -> User.withUsername(user.getUserName()) // ✅ Convert to Spring Security's UserDetails
                        .password(user.getPassword()) // Already encrypted
                        .roles("USER") // You can modify this based on your user roles
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public com.jwtpr.models.User saveUser(com.jwtpr.models.User user) { // Fully qualified to avoid confusion
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }
}
