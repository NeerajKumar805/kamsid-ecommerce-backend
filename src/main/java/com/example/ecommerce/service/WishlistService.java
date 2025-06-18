package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.WishlistItem;
import com.example.ecommerce.repository.WishlistRepository;

@Service
public class WishlistService {
	@Autowired
	private WishlistRepository wishlistRepository;

	public WishlistItem createWishlist(WishlistItem wishlist) {
		if (wishlistRepository.existsByProduct(wishlist.getProduct())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"WishlistItem with same title and description already exists");
		}
		return wishlistRepository.save(wishlist);
	}

	public List<WishlistItem> getAllWishlists() {
		return wishlistRepository.findAll();
	}
//
//	public WishlistItem getWishlistById(Long id) {
//		return WishlistRepository.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WishlistItem not found"));
//	}
//
//	public WishlistItem updateWishlist(Long id, WishlistItem updatedWishlist) {
//		WishlistItem existing = WishlistRepository.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "WishlistItem not found"));
//
//		if (!existing.getTitle().equals(updatedWishlist.getTitle())
//				|| !existing.getDescription().equals(updatedWishlist.getDescription())) {
//			if (WishlistRepository.existsByTitleAndDescription(updatedWishlist.getTitle(),
//					updatedWishlist.getDescription())) {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//						"Another WishlistItem with same title and description exists");
//			}
//		}
//
//		existing.setTitle(updatedWishlist.getTitle());
//		existing.setDescription(updatedWishlist.getDescription());
//		existing.setPrice(updatedWishlist.getPrice());
//		existing.setCategory(updatedWishlist.getCategory());
//		existing.setInStock(updatedWishlist.isInStock());
//
//		return WishlistRepository.save(existing);
//	}
//
//	public WishlistItem patchWishlist(Long id, WishlistItem patchData) {
//		WishlistItem existing = getWishlistById(id);
//
//		String newTitle = patchData.getTitle();
//		String newDesc = patchData.getDescription();
//		if (newTitle != null && newDesc != null
//				&& (!existing.getTitle().equals(newTitle) || !existing.getDescription().equals(newDesc))) {
//			if (WishlistRepository.existsByTitleAndDescription(newTitle, newDesc)) {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//						"Another WishlistItem with same title and description exists");
//			}
//		}
//
//		if (newTitle != null)
//			existing.setTitle(newTitle);
//		if (newDesc != null)
//			existing.setDescription(newDesc);
//		if (patchData.getPrice() != null)
//			existing.setPrice(patchData.getPrice());
//		if (patchData.getCategory() != null)
//			existing.setCategory(patchData.getCategory());
//		if (patchData.isInStock() != existing.isInStock())
//			existing.setInStock(patchData.isInStock());
//
//		return WishlistRepository.save(existing);
//	}
//
//	public void deleteWishlist(Long id) {
//		WishlistItem existing = getWishlistById(id);
//		WishlistRepository.delete(existing);
//	}
}
