package com.example.lets_shop_app.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.lets_shop_app.entity.Cart;

@RequestMapping("/api")
public interface CartRepository extends JpaRepository<Cart, Long>{

	List<Cart> findByEmail(String email);
	Cart findByProductIdAndEmail(Long productId, String email);
}
