package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/addresses")
@Validated
@CrossOrigin
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping
	public ResponseEntity<ApiResponse<Address>> createAddress(@Valid @RequestBody Address address) {
		Address created = addressService.createAddress(address);
		return ResponseEntity.status(201).body(new ApiResponse<>(true, "Address added successfully", created));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Address>>> getAllAddresses() {
		List<Address> list = addressService.getAllAddresses();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all addresses", list));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Address>> getAddressById(@PathVariable Long id) {
		Address address = addressService.getAddressById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched address", address));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Address>> updateAddress(@PathVariable Long id,
			@Valid @RequestBody Address updatedAddress) {
		Address address = addressService.updateAddress(id, updatedAddress);
		return ResponseEntity.ok(new ApiResponse<>(true, "Address updated successfully", address));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Address>> patchAddress(@PathVariable Long id, @RequestBody Address patchData) {
		Address updated = addressService.patchAddress(id, patchData);
		return ResponseEntity.ok(new ApiResponse<>(true, "Address patched successfully", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteAddress(@PathVariable Long id) {
		addressService.deleteAddress(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Address deleted successfully", "Deleted ID: " + id));
	}
}
