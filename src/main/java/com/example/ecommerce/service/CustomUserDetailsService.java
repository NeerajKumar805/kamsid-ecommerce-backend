//src/main/java/com/example/ecommerce/service/CustomUserDetailsService.java
package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String loginKey) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmailOrMobileNo(loginKey, loginKey, loginKey)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<GrantedAuthority> auths = List
				.of(new SimpleGrantedAuthority(user.isIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER"));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auths);
	}

}
