package com.gpcf.AuthApp.service;


import com.gpcf.AuthApp.DTO.RegisterDTO;
import com.gpcf.AuthApp.Repo.RoleRepository;
import com.gpcf.AuthApp.Repo.UserRepository;
import com.gpcf.AuthApp.model.RoleEntity;
import com.gpcf.AuthApp.model.userEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder  pe;

    public String registerUser(RegisterDTO dto) throws Exception {
        userEntity u=new userEntity();
        u.setUsername(dto.getUsername());
        u.setPassword(pe.encode(dto.getPassword()));



        for (String role : dto.getRoles()){
            RoleEntity byRoleName = roleRepository.findByRoleName(role)
                    .orElseThrow(()-> new  Exception("Role not found: " + role));
            u.getRole().add(byRoleName);
        }

        userRepository.save(u);

        return "User Registered Successfully!";
    }

}
