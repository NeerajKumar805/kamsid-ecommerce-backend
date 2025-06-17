// src/main/java/com/example/ecommerce/controller/AuthController.java
package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.payload.LoginRequest;
import com.example.ecommerce.security.JwtUtil;
import com.example.ecommerce.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    @Autowired private AuthenticationManager authManager;
    @Autowired private CustomUserDetailsService uds;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody @Valid LoginRequest req) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails ud = uds.loadUserByUsername(req.getUsername());
        String token = jwtUtil.generateToken(ud.getUsername(),
            ud.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        return ResponseEntity.ok(new ApiResponse<>(true, "Token generated", token));
    }
}
