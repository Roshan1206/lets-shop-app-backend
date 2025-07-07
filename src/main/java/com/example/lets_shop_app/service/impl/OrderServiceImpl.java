package com.example.lets_shop_app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.lets_shop_app.service.OrderService;
import com.example.lets_shop_app.util.UserUtil;
import org.springframework.stereotype.Service;

import com.example.lets_shop_app.dao.OrderRepository;
import com.example.lets_shop_app.dao.ProductRepository;
import com.example.lets_shop_app.entity.Order;
import com.example.lets_shop_app.entity.Product;
import com.example.lets_shop_app.dto.OrderCreateRequest;
import com.example.lets_shop_app.dto.OrderUserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final UserUtil userUtil;


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


	@Override
	public List<OrderUserResponse> getUserOrder() {
		String email = userUtil.getAuthenticatedUserEmail();
		List<Order> userOrders= orderRepository.findByEmail(email);
		List<OrderUserResponse> orderUserResponses = new ArrayList<>();

        for (Order order : userOrders) {
            OrderUserResponse tempOrderUserResponse = new OrderUserResponse();
            Optional<Product> product = productRepository.findById(order.getProductId());

            tempOrderUserResponse.setProductId(product.get().getId());
            tempOrderUserResponse.setProductName(product.get().getName());
            tempOrderUserResponse.setProductPrice(product.get().getPrice());
            tempOrderUserResponse.setProductThumbnail(product.get().getThumbnail());
            tempOrderUserResponse.setCreatedDate(order.getCreatedAt());
            tempOrderUserResponse.setProductQuantity(order.getProductQuantity());
            tempOrderUserResponse.setTotalPrice(order.getTotalPrice());

            orderUserResponses.add(tempOrderUserResponse);
        }
		
		return orderUserResponses;
	}
}
