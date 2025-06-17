// src/main/java/com/example/ecommerce/security/JwtAuthenticationFilter.java
package com.example.ecommerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ecommerce.service.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService uds;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService uds) {
		this.jwtUtil = jwtUtil;
		this.uds = uds;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String header = req.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			try {
				Claims claims = jwtUtil.validateToken(token).getBody();
				String userId = claims.getSubject();
				UserDetails ud = uds.loadUserByUsername(userId);
				Authentication auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception ignored) {
			}
		}
		chain.doFilter(req, res);
	}
}
