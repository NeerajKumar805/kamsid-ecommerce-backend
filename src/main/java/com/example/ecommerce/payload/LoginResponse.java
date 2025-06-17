// LoginResponse.java
package com.example.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String userId;
	private String username;
	private boolean IsActive;
	private boolean IsAdmin;
}
