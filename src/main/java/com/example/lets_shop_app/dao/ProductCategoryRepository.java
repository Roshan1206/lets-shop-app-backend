package com.example.lets_shop_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lets_shop_app.entity.ProductCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{

}
