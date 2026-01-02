package com.gpcf.AuthApp.service;

import com.gpcf.AuthApp.Repo.UserRepository;
import com.gpcf.AuthApp.model.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        userEntity user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found"));

        List<SimpleGrantedAuthority> authorities = user.getRole().stream().map(role -> new
                SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());

        return User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();


    }
}


