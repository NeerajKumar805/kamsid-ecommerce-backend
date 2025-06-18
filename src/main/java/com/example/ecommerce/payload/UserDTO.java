package com.example.ecommerce.payload;

import com.example.ecommerce.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String userId;
	private String username;
	private String mobileNo;
	private String email;
	private boolean IsActive;
	private boolean IsAdmin;

	// Constructor
	public UserDTO(User user) {
		this.userId = user.getUserId();
		this.username = user.getUsername();
		this.mobileNo = user.getMobileNo();
		this.email = user.getEmail();
		this.IsActive = user.isIsActive();
		this.IsAdmin = user.isIsAdmin();
	}

}