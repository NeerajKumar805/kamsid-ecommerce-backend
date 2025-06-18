// src/main/java/com/example/ecommerce/service/UserService.java
package com.example.ecommerce.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User createUser(User user) {
		// generate custom userId if needed
		if (user.getUserId() == null || user.getUserId().isEmpty()) {
			user.setUserId(generateCustomUserId(user.getUsername()));
		}

		// hash the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedAt(LocalDateTime.now());
		user.setUpdatedAt(LocalDateTime.now());

		try {
			return userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			throw e; // handled by GlobalExceptionHandler
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user");
		}
	}

	public User authenticate(String loginKey, String rawPassword) {
		User user = userRepository.findByUsernameOrEmailOrMobileNo(loginKey, loginKey, loginKey)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials"));

		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
		}
		return user;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
	}

	public User updateUser(String userId, User updatedUser) {
		User existing = getUserById(userId);

		existing.setUsername(updatedUser.getUsername());
		existing.setPassword(updatedUser.getPassword());
		existing.setMobileNo(updatedUser.getMobileNo());
		existing.setEmail(updatedUser.getEmail());
		existing.setIsActive(updatedUser.isIsActive());
		existing.setIsAdmin(updatedUser.isIsAdmin());
		existing.setUpdatedAt(java.time.LocalDateTime.now());

		try {
			return userRepository.save(existing);
		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update user");
		}
	}

	public User patchUser(String userId, User patchData) {
		User existing = getUserById(userId);

		if (patchData.getUsername() != null)
			existing.setUsername(patchData.getUsername());
		if (patchData.getPassword() != null)
			existing.setPassword(patchData.getPassword());
		if (patchData.getMobileNo() != null)
			existing.setMobileNo(patchData.getMobileNo());
		if (patchData.getEmail() != null)
			existing.setEmail(patchData.getEmail());
		if (patchData.isIsActive() != existing.isIsActive())
			existing.setIsActive(patchData.isIsActive());
		if (patchData.isIsAdmin() != existing.isIsAdmin())
			existing.setIsAdmin(patchData.isIsAdmin());

		existing.setUpdatedAt(LocalDateTime.now());

		try {
			return userRepository.save(existing);
		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to patch user");
		}
	}

	public void deleteUser(String userId) {
		User existing = getUserById(userId);
		userRepository.delete(existing);
	}

	private String generateCustomUserId(String username) {
		String clean = username.replaceAll("[^a-zA-Z]", "").toLowerCase();
		String shortName = clean.length() > 6 ? clean.substring(0, 6) : clean;
		String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		return shortName + timestamp;
	}
}
