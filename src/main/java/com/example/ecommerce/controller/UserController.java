// UserController.java
package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.payload.UserDTO;
import com.example.ecommerce.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
		User created = userService.createUser(user);
		return ResponseEntity.status(201).body(new ApiResponse<>(true, "Sign up successfully", created));
	}


	@GetMapping
	public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
		List<UserDTO> dtoList = userService.getAllUsers().stream().map(UserDTO::new).toList();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all users", dtoList));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable String id) {
		UserDTO user = new UserDTO(userService.getUserById(id));
		return ResponseEntity.ok(new ApiResponse<>(true, "User found", user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable String id,
			@Valid @RequestBody User updatedUser) {
		UserDTO user = new UserDTO(userService.updateUser(id, updatedUser));
		return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", user));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<User>> patchUser(@PathVariable String userId, @RequestBody User patchData) {
		User updated = userService.patchUser(userId, patchData);
		return ResponseEntity.ok(new ApiResponse<>(true, "User patched successfully", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", id));
	}

}
