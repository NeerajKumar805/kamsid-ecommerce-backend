// ProductController.java
package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody Product product) {
		Product created = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(true, "Product created successfully", created));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
		List<Product> list = productService.getAllProducts();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all products", list));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched product", product));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id,
			@Valid @RequestBody Product updatedProduct) {
		Product updated = productService.updateProduct(id, updatedProduct);
		return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", updated));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Product>> patchProduct(@PathVariable Long id, @RequestBody Product patchData) {
		Product updated = productService.patchProduct(id, patchData);
		return ResponseEntity.ok(new ApiResponse<>(true, "Product patched successfully", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted successfully", null));
	}
}
