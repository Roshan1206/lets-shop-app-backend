package com.example.lets_shop_app.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lets_shop_app.dao.CountryRepository;
import com.example.lets_shop_app.dao.StateRepository;
import com.example.lets_shop_app.entity.Country;
import com.example.lets_shop_app.entity.State;
import com.example.lets_shop_app.model.CountryResponse;
import com.example.lets_shop_app.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressContoller {

	private final AddressService addressService;
	
	@GetMapping("/countries")
	public List<CountryResponse> getCountries(){
		return addressService.getAllCountries();
	}
	
	@GetMapping("/states/{id}")
	public List<State> getStates(@PathVariable Integer id){
		return addressService.getStates(id);
	}
}
