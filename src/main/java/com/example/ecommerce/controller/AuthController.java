// src/main/java/com/example/ecommerce/controller/AuthController.java
package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.payload.LoginRequest;
import com.example.ecommerce.payload.LoginResponse;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserRepository userRepo;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest req) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

		User user = userRepo.findByUsernameOrEmailOrMobileNo(req.getUsername(), req.getUsername(), req.getUsername())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"));
		String token = jwtUtil.generateToken(user.getUsername(), user.isIsAdmin());

		LoginResponse resp = new LoginResponse();
		resp.setToken(token);
		resp.setUserId(user.getUserId());
		resp.setUsername(user.getUsername());
		resp.setActive(user.isIsActive());
		resp.setAdmin(user.isIsAdmin());

		return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", resp));
	}
}