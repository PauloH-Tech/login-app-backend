package com.example.loginauthapi.infra.security;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] parts = username.split("\\|");

        if (parts.length != 2){
            throw new UsernameNotFoundException("Invalid login format");
        }
        String email = parts[0];
        String descriptionApp = parts[1];

        User user = this.repository.findByEmailAndDescriptionApp(email, descriptionApp).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
