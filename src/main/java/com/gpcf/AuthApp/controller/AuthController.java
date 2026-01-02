package com.gpcf.AuthApp.controller;


import com.gpcf.AuthApp.DTO.LoginDTO;
import com.gpcf.AuthApp.DTO.RegisterDTO;
import com.gpcf.AuthApp.model.userEntity;
import com.gpcf.AuthApp.service.JwtService;
//import com.gpcf.AuthApp.service.LoginService;
import com.gpcf.AuthApp.service.UserService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService user;

//    @Autowired
//    private LoginService loginUser;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtService jwtService;



    // ---------------- PUBLIC ----------------
    @GetMapping("/public/about")
    public String about() {
        return "This is About Us (Public)";
    }

    @GetMapping("/public/contact")
    public String contact() {
        return "This is Contact Us (Public)";
    }

    @GetMapping("/public/home")
    public String home() {
        return "Welcome to Our API (Public)";
    }


    // ---------------- USER ONLY ----------------
    @GetMapping("/user/profile")
    public String userProfile() {
        return "User Profile Data (USER allowed)";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "User Dashboard (USER allowed)";
    }


    // ---------------- ADMIN ONLY ----------------
    @GetMapping("/admin/panel")
    public String adminPanel() {
        return "Admin Panel Data (ADMIN only)";
    }

    @GetMapping("/admin/users")
    public String adminUsers() {
        return "List of all users (ADMIN only)";
    }


    // ---------------- BOTH USER + ADMIN ----------------
    @GetMapping("/common/info")
    public String commonInfo() {
        return "This API is accessible by USER & ADMIN";
    }


    // ---------------- ANY JWT REQUIRED ----------------
    @GetMapping("/secure/data")
    public String secureData() {
        return "JWT Valid. Authenticated Successfully!";
    }



//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws Exception {
//        String token = loginUser.loginUser(loginDTO);
//        return ResponseEntity.ok(token);
//    }



    @PostMapping("/login")
    public String login(@RequestBody LoginDTO dto) {

        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(),
                dto.getPassword()
        ));


        UserDetails principal = (UserDetails) authenticate.getPrincipal();


        return jwtService.generateJwt(principal);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) throws Exception {
        return ResponseEntity.ok(user.registerUser(registerDTO));
    }

}
