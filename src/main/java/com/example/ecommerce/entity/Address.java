// src/main/java/com/example/ecommerce/entity/Address.java
package com.example.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "user_address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

	@NotBlank
	private String area;

	@NotBlank
	private String city;

	@NotBlank
	private String state;

	@NotBlank
	private String pin;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference("user-address")
	private User user;
}
