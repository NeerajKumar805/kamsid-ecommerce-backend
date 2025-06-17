package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Product createProduct(Product product) {
		if (productRepository.existsByTitleAndDescription(product.getTitle(), product.getDescription())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Product with same title and description already exists");
		}
		return productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
	}

	public Product updateProduct(Long id, Product updatedProduct) {
		Product existing = productRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

		if (!existing.getTitle().equals(updatedProduct.getTitle())
				|| !existing.getDescription().equals(updatedProduct.getDescription())) {
			if (productRepository.existsByTitleAndDescription(updatedProduct.getTitle(),
					updatedProduct.getDescription())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Another product with same title and description exists");
			}
		}

		existing.setTitle(updatedProduct.getTitle());
		existing.setDescription(updatedProduct.getDescription());
		existing.setPrice(updatedProduct.getPrice());
		existing.setCategory(updatedProduct.getCategory());
		existing.setInStock(updatedProduct.isInStock());

		return productRepository.save(existing);
	}

	public Product patchProduct(Long id, Product patchData) {
		Product existing = getProductById(id);

		String newTitle = patchData.getTitle();
		String newDesc = patchData.getDescription();
		if (newTitle != null && newDesc != null
				&& (!existing.getTitle().equals(newTitle) || !existing.getDescription().equals(newDesc))) {
			if (productRepository.existsByTitleAndDescription(newTitle, newDesc)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Another product with same title and description exists");
			}
		}

		if (newTitle != null)
			existing.setTitle(newTitle);
		if (newDesc != null)
			existing.setDescription(newDesc);
		if (patchData.getPrice() != null)
			existing.setPrice(patchData.getPrice());
		if (patchData.getCategory() != null)
			existing.setCategory(patchData.getCategory());
		if (patchData.isInStock() != existing.isInStock())
			existing.setInStock(patchData.isInStock());

		return productRepository.save(existing);
	}

	public void deleteProduct(Long id) {
		Product existing = getProductById(id);
		productRepository.delete(existing);
	}
}
