//src/main/java/com/example/ecommerce/security/JwtUtil.java
package com.example.ecommerce.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	private final long validity = 1000 * 60 * 60 * 24; // 24h

	public String generateToken(String username, boolean isAdmin) {
		return Jwts.builder().setSubject(username).claim("isAdmin", isAdmin).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + validity)).signWith(key).compact();
	}

	public Jws<Claims> validateToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}
}
