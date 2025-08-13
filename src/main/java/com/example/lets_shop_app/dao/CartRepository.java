package com.example.lets_shop_app.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lets_shop_app.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

	Optional<Cart> findByCreatedBy(long id);
}
