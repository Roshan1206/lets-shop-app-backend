package com.example.lets_shop_app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.lets_shop_app.entity.State;

public interface StateRepository extends JpaRepository<State, Long>{
	List<State> findByCountryId(@Param("id") Integer code);
}
