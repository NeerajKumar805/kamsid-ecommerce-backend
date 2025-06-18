// WishlistItemController.java
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

import com.example.ecommerce.entity.WishlistItem;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.service.WishlistService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/wishlist")
@Validated
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@PostMapping
	public ResponseEntity<ApiResponse<WishlistItem>> createWishlistItem(@Valid @RequestBody WishlistItem wishlistItem) {
		WishlistItem created = wishlistService.createWishlist(wishlistItem);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(true, "WishlistItem created successfully", created));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<WishlistItem>>> getAllWishlistItems() {
		List<WishlistItem> list = wishlistService.getAllWishlists();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all WishlistItems", list));
	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<ApiResponse<WishlistItem>> getWishlistItemById(@PathVariable Long id) {
//		WishlistItem WishlistItem = wishlistService.getWishlistItemById(id);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched WishlistItem", WishlistItem));
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<ApiResponse<WishlistItem>> updateWishlistItem(@PathVariable Long id,
//			@Valid @RequestBody WishlistItem updatedWishlistItem) {
//		WishlistItem updated = wishlistService.updateWishlistItem(id, updatedWishlistItem);
//		return ResponseEntity.ok(new ApiResponse<>(true, "WishlistItem updated successfully", updated));
//	}
//
//	@PatchMapping("/{id}")
//	public ResponseEntity<ApiResponse<WishlistItem>> patchWishlistItem(@PathVariable Long id, @RequestBody WishlistItem patchData) {
//		WishlistItem updated = wishlistService.patchWishlistItem(id, patchData);
//		return ResponseEntity.ok(new ApiResponse<>(true, "WishlistItem patched successfully", updated));
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<ApiResponse<Void>> deleteWishlistItem(@PathVariable Long id) {
//		wishlistService.deleteWishlistItem(id);
//		return ResponseEntity.ok(new ApiResponse<>(true, "WishlistItem deleted successfully", null));
//	}
}
