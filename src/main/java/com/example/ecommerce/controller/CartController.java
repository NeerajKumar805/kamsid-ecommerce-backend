// CartItemController.java
package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping
	public ResponseEntity<ApiResponse<CartItem>> createCartItem(@Valid @RequestBody CartItem cartItem) {
		CartItem created = cartService.createcartItem(cartItem);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(true, "CartItem created successfully", created));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<CartItem>>> getAllCartItems() {
		List<CartItem> list = cartService.getAllcartItems();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all CartItems", list));
	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<ApiResponse<CartItem>> getCartItemById(@PathVariable Long id) {
//		CartItem CartItem = cartService.getCartItemById(id);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched CartItem", CartItem));
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<ApiResponse<CartItem>> updateCartItem(@PathVariable Long id,
//			@Valid @RequestBody CartItem updatedCartItem) {
//		CartItem updated = cartService.updateCartItem(id, updatedCartItem);
//		return ResponseEntity.ok(new ApiResponse<>(true, "CartItem updated successfully", updated));
//	}
//
//	@PatchMapping("/{id}")
//	public ResponseEntity<ApiResponse<CartItem>> patchCartItem(@PathVariable Long id, @RequestBody CartItem patchData) {
//		CartItem updated = cartService.patchCartItem(id, patchData);
//		return ResponseEntity.ok(new ApiResponse<>(true, "CartItem patched successfully", updated));
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long id) {
//		cartService.deleteCartItem(id);
//		return ResponseEntity.ok(new ApiResponse<>(true, "CartItem deleted successfully", null));
//	}
}
