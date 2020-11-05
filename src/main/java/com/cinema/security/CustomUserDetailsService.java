package com.cinema.security;

import com.cinema.model.User;
import com.cinema.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            builder = org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail());
            builder.password(user.getPassword());
            builder.roles(user.getRoles().stream()
                    .map(role -> role.getRoleName().toString())
                    .toArray(String[]::new));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
