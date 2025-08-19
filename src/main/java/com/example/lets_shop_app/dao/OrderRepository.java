package com.example.lets_shop_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lets_shop_app.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	Optional<List<Order>> findByUserId(long id);
}
