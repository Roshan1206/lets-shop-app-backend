package com.example.lets_shop_app.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	
	public Long addOrder(OrderCreateRequest orderRequest, Principal principal) {
		Optional<Product> product = productRepository.findById(orderRequest.getProductId());
		final Long productId = product.get().getId();
		final Double totalPrice = product.get().getPrice()*orderRequest.getTotalQuantity();
		
		Order newOrder = new Order();
		newOrder.setEmail(principal.getName());
		newOrder.setProductId(productId);
		newOrder.setProductQuantity(orderRequest.getTotalQuantity());
		newOrder.setTotalPrice(totalPrice);
		
		Order savedOrder = orderRepository.save(newOrder);
		return savedOrder.getId();
	}
	
	public List<OrderUserResponse> getUserOrder(Principal principal) {
		String email = principal.getName();
		List<Order> userOrders= orderRepository.findByEmail(email);
		List<OrderUserResponse> orderUserResponses = new ArrayList<>();
		
		for (Iterator<Order> iterator = userOrders.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			OrderUserResponse tempOrderUserResponse = new OrderUserResponse();
			Optional<Product> product = productRepository.findById(order.getProductId());
			
			tempOrderUserResponse.setProductId(product.get().getId());
			tempOrderUserResponse.setProductName(product.get().getName());
			tempOrderUserResponse.setProductPrice(product.get().getPrice());
			tempOrderUserResponse.setProductThumbnail(product.get().getThumbnail());
			tempOrderUserResponse.setCreatedDate(order.getDateCreated());
			tempOrderUserResponse.setProductQuantity(order.getProductQuantity());
			tempOrderUserResponse.setTotalPrice(order.getTotalPrice());
			
			orderUserResponses.add(tempOrderUserResponse);
		}
		
//		for (Iterator<OrderUserResponse> iterator = orderUserResponses.iterator(); iterator.hasNext();) {
//			OrderUserResponse orderUserResponse = (OrderUserResponse) iterator.next();
//			System.out.println(orderUserResponse);
//		}
		return orderUserResponses;
	}
}
