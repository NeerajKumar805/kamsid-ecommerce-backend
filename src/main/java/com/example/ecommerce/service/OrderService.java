package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ProductRepository productRepository;

	public Order createOrder(Order order) {
		String userId = order.getUser() != null ? order.getUser().getUserId() : null;
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

		Long addressId = order.getShippingAddress() != null ? order.getShippingAddress().getAddressId() : null;
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping address not found"));

		if (order.getProducts() == null || order.getProducts().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one product is required");
		}

		List<Product> validatedProducts = order.getProducts().stream()
				.map(p -> productRepository.findById(p.getProductId())
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"Product not found with ID: " + p.getProductId())))
				.toList();

		// Update order with verified references
		order.setUser(user);
		order.setShippingAddress(address);
		order.setProducts(validatedProducts);

		return orderRepository.save(order);
	}

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public Order getOrderById(Long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
	}

	public Order updateOrder(Long id, Order updatedOrder) {
		Order existing = orderRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

		// Validate user
		String userId = updatedOrder.getUser() != null ? updatedOrder.getUser().getUserId() : null;
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

		// Validate address
		Long addressId = updatedOrder.getShippingAddress() != null ? updatedOrder.getShippingAddress().getAddressId()
				: null;
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping address not found"));

		// Validate product list
		if (updatedOrder.getProducts() == null || updatedOrder.getProducts().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "At least one product is required");
		}

		List<Product> validatedProducts = updatedOrder.getProducts().stream()
				.map(p -> productRepository.findById(p.getProductId())
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
								"Product not found with ID: " + p.getProductId())))
				.toList();

		// Update fields
		existing.setUser(user);
		existing.setShippingAddress(address);
		existing.setProducts(validatedProducts);
		existing.setOrderStatus(updatedOrder.getOrderStatus());
		existing.setTotalAmount(updatedOrder.getTotalAmount());
		existing.setOrderDate(updatedOrder.getOrderDate());

		return orderRepository.save(existing);
	}

	public Order patchOrder(Long id, Order patchData) {
		Order existing = getOrderById(id);

		if (patchData.getUser() != null && patchData.getUser().getUserId() != null) {
			User u = userRepository.findById(patchData.getUser().getUserId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
			existing.setUser(u);
		}

		if (patchData.getShippingAddress() != null && patchData.getShippingAddress().getAddressId() != null) {
			Address a = addressRepository.findById(patchData.getShippingAddress().getAddressId()).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipping address not found"));
			existing.setShippingAddress(a);
		}

		if (patchData.getProducts() != null && !patchData.getProducts().isEmpty()) {
			List<Product> validated = patchData.getProducts().stream()
					.map(p -> productRepository.findById(p.getProductId())
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
									"Product not found with ID: " + p.getProductId())))
					.toList();
			existing.setProducts(validated);
		}

		if (patchData.getOrderStatus() != null)
			existing.setOrderStatus(patchData.getOrderStatus());
		if (patchData.getTotalAmount() != null)
			existing.setTotalAmount(patchData.getTotalAmount());
		if (patchData.getOrderDate() != null)
			existing.setOrderDate(patchData.getOrderDate());

		return orderRepository.save(existing);
	}

	public void deleteOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
		orderRepository.delete(order);
	}
}
