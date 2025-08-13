package com.example.lets_shop_app.service.impl;

import java.util.List;

import com.example.lets_shop_app.entity.Cart;
import com.example.lets_shop_app.entity.CartItem;
import com.example.lets_shop_app.service.OrderService;
import com.example.lets_shop_app.util.CartUtil;
import com.example.lets_shop_app.util.ProductUtil;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.OrderRepository;
import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.entity.Order;
import com.example.lets_shop_app.entity.Product;
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
	 * Injecting {@link com.example.lets_shop_app.util.CartUtil}, {@link OrderRepository}, {@link ProductRepository}, {@link UserUtil}
	 */
	private final CartUtil cartUtil;
	private final OrderRepository orderRepository;
	private final ProductUtil productUtil;
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
		List<Order> orders = orderRepository.findByCreatedBy(userUtil.getUserId());
		return orders.isEmpty() ? List.of() : orders;
	}

	/**
	 * Retrieve specific order
	 *
	 * @param id order id
	 * @return order
	 */
	@Override
	public OrderUserResponse getUserOrder(long id) {
		return null;
	}
}
