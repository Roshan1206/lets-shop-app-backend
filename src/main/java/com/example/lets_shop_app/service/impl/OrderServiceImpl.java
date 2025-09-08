package com.example.lets_shop_app.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.lets_shop_app.entity.CartItem;
import com.example.lets_shop_app.service.OrderService;
import com.example.lets_shop_app.util.CartUtil;
import com.example.lets_shop_app.util.OrderUtil;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.repository.OrderRepository;
import com.example.lets_shop_app.repository.ProductRepository;
import com.example.lets_shop_app.entity.Order;
import com.example.lets_shop_app.dto.response.OrderUserResponse;

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
	 * Injecting {@link com.example.lets_shop_app.util.CartUtil}, {@link OrderRepository}, {@link ProductRepository}, {@link UserUtil}
	 */
	private final CartUtil cartUtil;
	private final OrderRepository orderRepository;
	private final OrderUtil orderUtil;
	private final UserUtil userUtil;


	/**
	 * Create new order for user.
	 *
	 * @param cartId details to create new order
	 * @return order id
	 */
	@Override
	public long addOrder(long cartId) {
		CartItem cartItem = cartUtil.getCartItem(cartId);
		Order order = new Order();

		order.setProductId(cartItem.getProduct().getId());
		order.setProductPrice(cartItem.getProductPrice());
		order.setProductQuantity(cartItem.getProductQuantity());
		order.setTotalPrice(cartItem.getTotalPrice());

		return orderRepository.save(order).getId();
	}

	/**
	 * Retrieve all orders for specific user
	 *
	 * @return List of orders
	 */
	@Override
	public List<OrderUserResponse> getUserOrder() {
		Optional<List<Order>> orders = orderRepository.findByUserId(userUtil.getUserId());

		if(orders.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No order has been placed yet");
		}
		return orders.get().stream().map(orderUtil::convertToOrderUserResponse).toList();
	}

	/**
	 * Retrieve specific order
	 *
	 * @param id order id
	 * @return order
	 */
	@Override
	public OrderUserResponse getUserOrder(long id) {
		Optional<Order> order = orderRepository.findById(id);

		if (order.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
		}

		return orderUtil.convertToOrderUserResponse(order.get());
	}
}
