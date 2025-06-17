// src/main/java/com/example/ecommerce/entity/User.java
package com.example.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(length = 50)
	private String userId;

	@NotBlank
	private String username;

	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String passwordHash;

	private boolean IsActive = true;

	private boolean IsAdmin = false;

	@Column(unique = true)
	@NotBlank
	private String mobileNo;
	
	@Column(unique = true)
	@NotBlank
	private String email;

	private LocalDateTime createdAt = LocalDateTime.now();
	private LocalDateTime updatedAt = LocalDateTime.now();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference("user-address")
	private List<Address> addresses;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonManagedReference("user-order")
	private List<Order> orders;
}
