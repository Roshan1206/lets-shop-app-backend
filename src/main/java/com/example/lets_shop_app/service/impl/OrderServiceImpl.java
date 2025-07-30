package com.example.lets_shop_app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.lets_shop_app.service.OrderService;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.OrderRepository;
import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.entity.Order;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.dto.OrderCreateRequest;
import com.example.lets_shop_app.dto.OrderUserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;


/**
 * Service class for Order related operations.
 *
 * @author Roshan
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


	/**
	 * Injecting {@link ProductRepository}, {@link OrderRepository}, {@link UserUtil}
	 */
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final UserUtil userUtil;


	/**
	 * Create new order for user.
	 *
	 * @param orderRequest details to create new order
	 * @return order id
	 */
	@Override
	public long addOrder(OrderCreateRequest orderRequest) {
		String userEmail = userUtil.getAuthenticatedUserEmail();
		Optional<Product> product = productRepository.findById(orderRequest.getProductId());
		final long productId = product.get().getId();
		final Double totalPrice = product.get().getPrice()*orderRequest.getTotalQuantity();
		
		Order newOrder = new Order();
		newOrder.setEmail(userEmail);
		newOrder.setProductId(productId);
		newOrder.setProductQuantity(orderRequest.getTotalQuantity());
		newOrder.setTotalPrice(totalPrice);
		
		Order savedOrder = orderRepository.save(newOrder);
		return savedOrder.getId();
	}


	/**
	 * Retrieve all orders for specific user
	 *
	 * @return List of orders
	 */
	@Override
	public List<OrderUserResponse> getUserOrder() {
		String email = userUtil.getAuthenticatedUserEmail();
		List<Order> userOrders= orderRepository.findByEmail(email);

		return userOrders.stream().map(this::convertToOrderUserResponse).toList();
	}


	/**
	 * Retrieve specific order
	 *
	 * @param id order id
	 * @return order
	 */
	@Override
	public OrderUserResponse getUserOrder(long id) {
		String email = userUtil.getAuthenticatedUserEmail();
		Order order = orderRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
		);
		return convertToOrderUserResponse(order);
	}


	/**
	 * Create {@link OrderUserResponse} object
	 *
	 * @param order Object to be created for
	 * @return OrderUserResponse
	 */
	public OrderUserResponse convertToOrderUserResponse(Order order){
		OrderUserResponse tempOrderUserResponse = new OrderUserResponse();
		Product product = productRepository.findById(order.getProductId()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
		);

		tempOrderUserResponse.setProductId(product.getId());
		tempOrderUserResponse.setProductName(product.getName());
		tempOrderUserResponse.setProductPrice(product.getPrice());
		tempOrderUserResponse.setProductThumbnail(product.getThumbnail());
		tempOrderUserResponse.setCreatedDate(order.getCreatedAt());
		tempOrderUserResponse.setProductQuantity(order.getProductQuantity());
		tempOrderUserResponse.setTotalPrice(order.getTotalPrice());

		return tempOrderUserResponse;
	}
}
