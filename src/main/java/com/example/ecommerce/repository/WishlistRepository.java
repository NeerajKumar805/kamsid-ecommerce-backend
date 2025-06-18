package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.WishlistItem;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
	boolean existsByProduct(Product product);
}
