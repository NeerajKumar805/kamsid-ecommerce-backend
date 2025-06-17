package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUsernameOrEmailOrMobileNoAndPassword(String username, String email, String mobileNo,
			String password);

	Optional<User> findByUsernameOrEmailOrMobileNo(String loginKey, String loginKey2, String loginKey3);
}
