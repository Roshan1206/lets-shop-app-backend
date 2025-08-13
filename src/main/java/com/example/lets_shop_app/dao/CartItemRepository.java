package com.example.lets_shop_app.dao;

import com.example.lets_shop_app.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<List<CartItem>> findAllByCreatedBy(long id);
}
