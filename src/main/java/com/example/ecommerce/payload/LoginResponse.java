// LoginResponse.java
package com.example.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private String userId;
	private String username;
	private boolean isActive;
	private boolean isAdmin;
}
