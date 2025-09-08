package com.example.lets_shop_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lets_shop_app.entity.User;


public interface UserRepository extends JpaRepository<User, String>{
	Optional<User> findByEmail(String email);

}
