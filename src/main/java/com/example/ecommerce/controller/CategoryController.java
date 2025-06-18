// CategoryController.java
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

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<ApiResponse<Category>> createCategory(@Valid @RequestBody Category Category) {
		Category created = categoryService.createCategory(Category);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(true, "Category created successfully", created));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Category>>> getAllCategorys() {
		List<Category> list = categoryService.getAllCategories();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all Categorys", list));
	}
//
//	@GetMapping("/{id}")
//	public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable Long id) {
//		Category Category = CategoryService.getCategoryById(id);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched Category", Category));
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Long id,
//			@Valid @RequestBody Category updatedCategory) {
//		Category updated = CategoryService.updateCategory(id, updatedCategory);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Category updated successfully", updated));
//	}
//
//	@PatchMapping("/{id}")
//	public ResponseEntity<ApiResponse<Category>> patchCategory(@PathVariable Long id, @RequestBody Category patchData) {
//		Category updated = CategoryService.patchCategory(id, patchData);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Category patched successfully", updated));
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
//		CategoryService.deleteCategory(id);
//		return ResponseEntity.ok(new ApiResponse<>(true, "Category deleted successfully", null));
//	}
}
