package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.repository.CartRepository;

@Service
public class CartService {
	@Autowired
	private CartRepository cartRepository;

	public CartItem createcartItem(CartItem cartItem) {
		if (cartRepository.existsByProduct(cartItem.getProduct())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item already added..");
		}
		return cartRepository.save(cartItem);
	}

	public List<CartItem> getAllcartItems() {
		return cartRepository.findAll();
	}
//
//	public CartItem getcartItemById(Long id) {
//		return cartRepository.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem not found"));
//	}
//
//	public CartItem updatecartItem(Long id, CartItem updatedcartItem) {
//		CartItem existing = cartRepository.findById(id)
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CartItem not found"));
//
//		if (!existing.getTitle().equals(updatedcartItem.getTitle())
//				|| !existing.getDescription().equals(updatedcartItem.getDescription())) {
//			if (cartRepository.existsByTitleAndDescription(updatedcartItem.getTitle(),
//					updatedcartItem.getDescription())) {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//						"Another CartItem with same title and description exists");
//			}
//		}
//
//		existing.setTitle(updatedcartItem.getTitle());
//		existing.setDescription(updatedcartItem.getDescription());
//		existing.setPrice(updatedcartItem.getPrice());
//		existing.setCategory(updatedcartItem.getCategory());
//		existing.setInStock(updatedcartItem.isInStock());
//
//		return cartRepository.save(existing);
//	}
//
//	public CartItem patchcartItem(Long id, CartItem patchData) {
//		CartItem existing = getcartItemById(id);
//
//		String newTitle = patchData.getTitle();
//		String newDesc = patchData.getDescription();
//		if (newTitle != null && newDesc != null
//				&& (!existing.getTitle().equals(newTitle) || !existing.getDescription().equals(newDesc))) {
//			if (cartRepository.existsByTitleAndDescription(newTitle, newDesc)) {
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//						"Another CartItem with same title and description exists");
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
//		return cartRepository.save(existing);
//	}
//
//	public void deletecartItem(Long id) {
//		CartItem existing = getcartItemById(id);
//		cartRepository.delete(existing);
//	}
}
