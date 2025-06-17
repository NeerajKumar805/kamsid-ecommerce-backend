// OrderController.java
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

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.payload.ApiResponse;
import com.example.ecommerce.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Validated
@CrossOrigin
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<ApiResponse<Order>> createOrder(@Valid @RequestBody Order order) {
		Order created = orderService.createOrder(order);
		return ResponseEntity.status(201).body(new ApiResponse<>(true, "Order placed successfully", created));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
		List<Order> list = orderService.getAllOrders();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched all orders", list));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Long id) {
		Order order = orderService.getOrderById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Order fetched", order));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Long id,
			@Valid @RequestBody Order updatedOrder) {
		Order updated = orderService.updateOrder(id, updatedOrder);
		return ResponseEntity.ok(new ApiResponse<>(true, "Order updated successfully", updated));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Order>> patchOrder(@PathVariable Long id, @RequestBody Order patchData) {
		Order updated = orderService.patchOrder(id, patchData);
		return ResponseEntity.ok(new ApiResponse<>(true, "Order patched successfully", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
		orderService.deleteOrder(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Order deleted successfully", null));
	}
}
