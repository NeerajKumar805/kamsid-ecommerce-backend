package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;

public interface CartRepository extends JpaRepository<CartItem, Long> {
	boolean existsByProduct(Product product);
}
