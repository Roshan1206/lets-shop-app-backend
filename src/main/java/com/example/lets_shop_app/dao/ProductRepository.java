package com.example.lets_shop_app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.lets_shop_app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	Page<Product> findByNameContaining(@Param("name") String name, Pageable pageable);
	
//	@Query("SELECT * FROM Product WHERE category=?;")
	Page<Product> findByCategory(@Param("category") long category, Pageable pageable);

	Page<Product> findByCategoryAndNameContaining(@Param("category") long category, @Param("name") String name, Pageable pageable);
}
